package com.automationcompany.notification.controller;

import com.automationcompany.notification.model.UserMessage;
import com.automationcompany.notification.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    @Operation(summary = "Get my messages")
    public ResponseEntity<List<UserMessage>> getMyMessages(
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {

        String email = jwt.getClaimAsString("email");
        return ResponseEntity.ok(messageService.getMessagesForUserByEmail(email));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        return ResponseEntity.ok(messageService.countUnreadByEmail(email));
    }

    @PatchMapping("/{messageId}/read")
    @Operation(summary = "Mark message as read")
    public ResponseEntity<Void> markAsRead(@PathVariable String messageId) {
        messageService.markAsRead(messageId);
        return ResponseEntity.noContent().build();
    }
}