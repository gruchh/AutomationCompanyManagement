package com.automationcompany.employee.controller;

import com.automationcompany.employee.model.dto.MessageDTO;
import com.automationcompany.employee.model.dto.SendMessageDTO;
import com.automationcompany.employee.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Employee messaging API")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @Operation(summary = "Send a message to another employee")
    public ResponseEntity<MessageDTO> sendMessage(
            @Valid @RequestBody SendMessageDTO dto,
            Authentication authentication) {

        Long senderId = 1L;

        MessageDTO messageDTO = messageService.sendMessageAndReturnDto(
                senderId,
                dto.getRecipientId(),
                dto.getSubject(),
                dto.getContent()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(messageDTO);
    }

    @GetMapping("/received")
    @Operation(summary = "Get messages received by the authenticated user")
    public ResponseEntity<List<MessageDTO>> getReceivedMessages(Authentication authentication) {
        Long userId = 1L;
        return ResponseEntity.ok(messageService.getMessagesForRecipient(userId));
    }

    @GetMapping("/sent")
    @Operation(summary = "Get messages sent by the authenticated user")
    public ResponseEntity<List<MessageDTO>> getSentMessages(Authentication authentication) {
        Long userId = 1L;
        return ResponseEntity.ok(messageService.getMessagesBySender(userId));
    }

    @GetMapping("/{messageId}")
    @Operation(summary = "Get a single message by its ID")
    public ResponseEntity<MessageDTO> getMessage(
            @Parameter(description = "Message ID", example = "101")
            @PathVariable Long messageId,
            Authentication authentication) {

        Long userId = 1L;
        return ResponseEntity.ok(messageService.getMessageById(messageId, userId));
    }

    @PatchMapping("/{messageId}/read")
    @Operation(summary = "Mark a message as read")
    public ResponseEntity<Void> markAsRead(
            @Parameter(description = "Message ID", example = "101")
            @PathVariable Long messageId,
            Authentication authentication) {

        Long userId = 1L;
        messageService.markAsRead(messageId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}")
    @Operation(summary = "Delete a message (soft delete)")
    public ResponseEntity<Void> deleteMessage(
            @Parameter(description = "Message ID", example = "101")
            @PathVariable Long messageId,
            Authentication authentication) {

        Long userId = 1L;
        messageService.deleteMessage(messageId, userId);
        return ResponseEntity.noContent().build();
    }
}
