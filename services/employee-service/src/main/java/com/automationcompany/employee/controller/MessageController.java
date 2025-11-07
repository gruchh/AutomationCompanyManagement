package com.automationcompany.employee.controller;

import com.automationcompany.employee.model.MessageCategory;
import com.automationcompany.employee.model.MessagePriority;
import com.automationcompany.employee.model.dto.MessageDto;
import com.automationcompany.employee.model.dto.SendMessageDto;
import com.automationcompany.employee.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Employee messaging API")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @Operation(summary = "Send a message to another employee")
    public ResponseEntity<MessageDto> sendMessage(
            @Valid @RequestBody SendMessageDto dto,
            Authentication authentication) {

        Long senderId = getCurrentUserId(authentication);
        MessageDto messageDTO = messageService.sendMessage(senderId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageDTO);
    }

    @PostMapping("/system")
    @Operation(summary = "Send a system message (admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageDto> sendSystemMessage(
            @Valid @RequestBody SendMessageDto dto) {

        MessageDto messageDTO = messageService.sendSystemMessage(
                dto.getRecipientId(),
                dto.getSubject(),
                dto.getContent(),
                dto.getCategory(),
                dto.getPriority()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(messageDTO);
    }

    @PostMapping("/system/broadcast")
    @Operation(summary = "Broadcast system message to all employees (admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MessageDto>> broadcastSystemMessage(
            @RequestParam String subject,
            @RequestParam String content,
            @RequestParam(defaultValue = "GENERAL") MessageCategory category,
            @RequestParam(defaultValue = "MEDIUM") MessagePriority priority) {

        List<MessageDto> messages = messageService.broadcastSystemMessage(
                subject, content, category, priority);
        return ResponseEntity.status(HttpStatus.CREATED).body(messages);
    }

    @GetMapping
    @Operation(summary = "Get all messages for the authenticated user")
    public ResponseEntity<List<MessageDto>> getAllMessages(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        return ResponseEntity.ok(messageService.getMessagesForRecipient(userId));
    }

    @GetMapping("/unread")
    @Operation(summary = "Get unread messages")
    public ResponseEntity<List<MessageDto>> getUnreadMessages(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        return ResponseEntity.ok(messageService.getUnreadMessages(userId));
    }

    @GetMapping("/unread/count")
    @Operation(summary = "Get unread messages count")
    public ResponseEntity<Long> getUnreadCount(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        return ResponseEntity.ok(messageService.getUnreadCount(userId));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get messages by category")
    public ResponseEntity<List<MessageDto>> getMessagesByCategory(
            @PathVariable MessageCategory category,
            Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        return ResponseEntity.ok(messageService.getMessagesByCategory(userId, category));
    }

    @GetMapping("/received")
    @Operation(summary = "Get messages received by the authenticated user")
    public ResponseEntity<List<MessageDto>> getReceivedMessages(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        return ResponseEntity.ok(messageService.getMessagesForRecipient(userId));
    }

    @GetMapping("/sent")
    @Operation(summary = "Get messages sent by the authenticated user")
    public ResponseEntity<List<MessageDto>> getSentMessages(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        return ResponseEntity.ok(messageService.getMessagesBySender(userId));
    }

    @GetMapping("/{messageId}")
    @Operation(summary = "Get a single message by its ID")
    public ResponseEntity<MessageDto> getMessage(
            @PathVariable Long messageId,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        return ResponseEntity.ok(messageService.getMessageById(messageId, userId));
    }

    @PatchMapping("/{messageId}/read")
    @Operation(summary = "Mark a message as read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long messageId,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        messageService.markAsRead(messageId, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/read-all")
    @Operation(summary = "Mark all messages as read")
    public ResponseEntity<Void> markAllAsRead(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        messageService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}")
    @Operation(summary = "Delete a message (soft delete)")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long messageId,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        messageService.deleteMessage(messageId, userId);
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId(Authentication authentication) {
        return 1L;
    }
}
