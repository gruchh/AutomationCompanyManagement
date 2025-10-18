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
@RequestMapping("/api/employees/{recipientId}/messages")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Employee messaging API")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @Operation(summary = "Send message", description = "Sends message to specified employee")
    public ResponseEntity<MessageDTO> sendMessage(
            @Parameter(description = "Recipient employee ID")
            @PathVariable Long recipientId,
            @Valid @RequestBody SendMessageDTO dto,
            Authentication authentication) {

        // W prawdziwej aplikacji pobierz z JWT/SecurityContext
        Long senderId = 1L; // Hardcoded dla portfolio
        MessageDTO messageDTO = messageService.sendMessageAndReturnDto(
                senderId, recipientId, dto.getSubject(), dto.getContent());

        return ResponseEntity.status(HttpStatus.CREATED).body(messageDTO);
    }

    @GetMapping
    @Operation(summary = "Get received messages", description = "Returns messages received by current user")
    public ResponseEntity<List<MessageDTO>> getReceivedMessages(
            @Parameter(description = "Current user ID")
            @RequestParam Long userId) {

        List<MessageDTO> messages = messageService.getMessagesForRecipient(userId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sent")
    @Operation(summary = "Get sent messages", description = "Returns messages sent by current user")
    public ResponseEntity<List<MessageDTO>> getSentMessages(
            @Parameter(description = "Current user ID")
            @RequestParam Long userId) {

        List<MessageDTO> messages = messageService.getMessagesBySender(userId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{messageId}")
    @Operation(summary = "Get single message", description = "Returns specific message with access check")
    public ResponseEntity<MessageDTO> getMessage(
            @Parameter(description = "Message ID")
            @PathVariable Long messageId,
            @Parameter(description = "Current user ID")
            @RequestParam Long userId) {

        MessageDTO message = messageService.getMessageById(messageId, userId);
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{messageId}/read")
    @Operation(summary = "Mark message as read", description = "Marks specific message as read")
    public ResponseEntity<Void> markAsRead(
            @Parameter(description = "Message ID")
            @PathVariable Long messageId,
            @Parameter(description = "Current user ID")
            @RequestParam Long userId) {

        messageService.markAsRead(messageId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}")
    @Operation(summary = "Delete message", description = "Soft deletes specific message")
    public ResponseEntity<Void> deleteMessage(
            @Parameter(description = "Message ID")
            @PathVariable Long messageId,
            @Parameter(description = "Current user ID")
            @RequestParam Long userId) {

        messageService.deleteMessage(messageId, userId);
        return ResponseEntity.noContent().build();
    }
}