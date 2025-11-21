package com.automationcompany.notification.controller;

import com.automationcompany.notification.model.UserMessage;
import com.automationcompany.notification.service.MessageService;
import com.automationcompany.notification.service.MessageStorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    @Operation(summary = "Get my messages", description = "Returns list of messages for current user")
    public ResponseEntity<List<UserMessage>> getMyMessages(
            @RequestHeader(name = "X-User-Id") Long userId) { 

        return ResponseEntity.ok(messageService.getMessagesForUser(userId));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@RequestHeader(name = "X-User-Id") Long userId) {
        return ResponseEntity.ok(messageService.countUnread(userId));
    }

    @PatchMapping("/{messageId}/read")
    @Operation(summary = "Mark message as read")
    public ResponseEntity<Void> markAsRead(@PathVariable String messageId) {
        messageService.markAsRead(messageId);
        return ResponseEntity.noContent().build();
    }
}