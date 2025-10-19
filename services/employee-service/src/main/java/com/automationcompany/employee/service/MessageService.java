package com.automationcompany.employee.service;

import com.automationcompany.employee.exception.EmployeeNotFoundException;
import com.automationcompany.employee.mapper.MessageMapper;
import com.automationcompany.employee.model.Employee;
import com.automationcompany.employee.model.Message;
import com.automationcompany.employee.model.dto.MessageDTO;
import com.automationcompany.employee.model.dto.SendMessageDTO;
import com.automationcompany.employee.repository.EmployeeRepository;
import com.automationcompany.employee.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final EmployeeRepository employeeRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    public MessageDTO sendMessageAndReturnDto(Long senderId, Long recipientId, String subject, String content) {
        log.info("Sending message from senderId={} to recipientId={}", senderId, recipientId);

        Employee sender = employeeRepository.findById(senderId)
                .orElseThrow(() -> new EmployeeNotFoundException("Sender not found: " + senderId));

        Employee recipient = employeeRepository.findById(recipientId)
                .orElseThrow(() -> new EmployeeNotFoundException("Recipient not found: " + recipientId));

        Message message = Message.builder()
                .sender(sender)
                .recipient(recipient)
                .subject(subject)
                .content(content)
                .isRead(false)
                .isDeleted(false)
                .sentAt(LocalDateTime.now())
                .build();

        Message savedMessage = messageRepository.save(message);

        MessageDTO messageDTO = messageMapper.toDTO(savedMessage);

        // Wysłanie powiadomienia do Kafka
        kafkaTemplate.send("employee-messages", recipient.getEmail(), messageDTO);
        log.info("Message sent successfully with ID={}", savedMessage.getId());

        return messageDTO;
    }

    public MessageDTO sendMessage(Long senderId, Long recipientId, SendMessageDTO dto) {
        return sendMessageAndReturnDto(senderId, recipientId, dto.getSubject(), dto.getContent());
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getMessagesForRecipient(Long recipientId) {
        log.info("Fetching messages for recipient: {}", recipientId);

        return messageRepository.findByRecipientIdAndIsDeletedFalseOrderBySentAtDesc(recipientId)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getMessagesBySender(Long senderId) {
        log.info("Fetching messages sent by sender: {}", senderId);

        return messageRepository.findBySenderIdAndIsDeletedFalseOrderBySentAtDesc(senderId)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MessageDTO getMessageById(Long messageId, Long userId) {
        log.info("Fetching message {} for user {}", messageId, userId);

        Message message = messageRepository.findByIdAndRecipientId(messageId, userId)
                .orElseThrow(() -> new EmployeeNotFoundException("Message not found or access denied"));

        return messageMapper.toDTO(message);
    }

    public void markAsRead(Long messageId, Long userId) {
        log.info("Marking message {} as read for user {}", messageId, userId);

        Message message = messageRepository.findByIdAndRecipientId(messageId, userId)
                .orElseThrow(() -> new EmployeeNotFoundException("Message not found or access denied"));

        if (Boolean.TRUE.equals(message.getIsRead())) {
            log.debug("Message {} is already marked as read", messageId);
            return;
        }

        message.setIsRead(true);
        message.setReadAt(LocalDateTime.now());
        messageRepository.save(message);

        log.info("Message {} marked as read", messageId);
    }

    public void deleteMessage(Long messageId, Long userId) {
        log.info("Deleting message {} for user {}", messageId, userId);

        Message message = messageRepository.findByIdAndRecipientId(messageId, userId)
                .orElseThrow(() -> new EmployeeNotFoundException("Message not found or access denied"));

        message.setIsDeleted(true);
        messageRepository.save(message);

        log.info("Message {} soft-deleted", messageId);
    }
}