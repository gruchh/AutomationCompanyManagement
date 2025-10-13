package com.automationcompany.employee.controller;

import com.automationcompany.employee.model.dto.MessageDTO;
import com.automationcompany.employee.model.dto.SendMessageRequest;
import com.automationcompany.employee.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send/{senderId}")
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
    public ResponseEntity<List<MessageDTO>> getReceivedMessages(@PathVariable Long employeeId) {
        List<MessageDTO> messages = messageService.getReceivedMessages(employeeId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sent/{employeeId}")
    public ResponseEntity<List<MessageDTO>> getSentMessages(@PathVariable Long employeeId) {
        List<MessageDTO> messages = messageService.getSentMessages(employeeId);
        return ResponseEntity.ok(messages);
    }

    @PutMapping("/{messageId}/read/{employeeId}")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long messageId,
            @PathVariable Long employeeId) {
        
        messageService.markAsRead(messageId, employeeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}/{employeeId}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long messageId,
            @PathVariable Long employeeId) {
        
        messageService.deleteMessage(messageId, employeeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{messageId}/is-read")
    public ResponseEntity<Boolean> isMessageRead(@PathVariable Long messageId) {
        boolean isRead = messageService.isMessageRead(messageId);
        return ResponseEntity.ok(isRead);
    }
}