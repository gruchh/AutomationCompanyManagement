package com.automationcompany.employee.controller;

import com.automationcompany.employee.model.dto.MessageDTO;
import com.automationcompany.employee.model.dto.SendMessageRequest;
import com.automationcompany.employee.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Message Management", description = "API for sending and managing messages between employees")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send/{senderId}")
    @Operation(summary = "Send message", description = "Sends a message from sender to recipient")
    public ResponseEntity<MessageDTO> sendMessage(
            @PathVariable Long senderId,
            @RequestBody SendMessageRequest request) {

        MessageDTO message = messageService.sendMessage(
                senderId,
                request.getRecipientId(),
                request.getSubject(),
                request.getContent()
        );

        return ResponseEntity.ok(message);
    }

    @GetMapping("/received/{employeeId}")
    @Operation(summary = "Get received messages", description = "Retrieves all messages received by employee")
    public ResponseEntity<List<MessageDTO>> getReceivedMessages(
            @PathVariable Long employeeId) {
        List<MessageDTO> messages = messageService.getReceivedMessages(employeeId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sent/{employeeId}")
    @Operation(summary = "Get sent messages", description = "Retrieves all messages sent by employee")
    public ResponseEntity<List<MessageDTO>> getSentMessages(
            @PathVariable Long employeeId) {
        List<MessageDTO> messages = messageService.getSentMessages(employeeId);
        return ResponseEntity.ok(messages);
    }

    @PutMapping("/{messageId}/read/{employeeId}")
    @Operation(summary = "Mark message as read", description = "Marks message as read for specific employee")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long messageId,
            @PathVariable Long employeeId) {

        messageService.markAsRead(messageId, employeeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}/{employeeId}")
    @Operation(summary = "Delete message", description = "Deletes message for specific employee")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long messageId,
            @PathVariable Long employeeId) {

        messageService.deleteMessage(messageId, employeeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{messageId}/is-read")
    @Operation(summary = "Check if message is read", description = "Checks if message has been read")
    public ResponseEntity<Boolean> isMessageRead(
            @PathVariable Long messageId) {
        boolean isRead = messageService.isMessageRead(messageId);
        return ResponseEntity.ok(isRead);
    }
}