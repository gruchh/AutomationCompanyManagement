package com.automationcompany.employee.service;

import com.automationcompany.employee.exception.EmployeeNotFoundException;
import com.automationcompany.employee.mapper.MessageMapper;
import com.automationcompany.employee.model.*;
import com.automationcompany.employee.model.dto.MessageDto;
import com.automationcompany.employee.model.dto.SendMessageDto;
import com.automationcompany.employee.repository.EmployeeRepository;
import com.automationcompany.employee.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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
    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
    private static final String KAFKA_TOPIC = "employee-messages";

    public MessageDto sendMessage(Long senderId, SendMessageDto dto) {
        log.info("Sending message from senderId={} to recipientId={}", senderId, dto.getRecipientId());

        Employee sender = employeeRepository.findById(senderId)
                .orElseThrow(() -> new EmployeeNotFoundException("Sender not found: " + senderId));

        Employee recipient = employeeRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new EmployeeNotFoundException("Recipient not found: " + dto.getRecipientId()));

        Message message = Message.builder()
                .sender(sender)
                .recipient(recipient)
                .subject(dto.getSubject())
                .content(dto.getContent())
                .type(MessageType.USER)
                .category(dto.getCategory())
                .priority(dto.getPriority())
                .isRead(false)
                .isDeleted(false)
                .build();

        Message savedMessage = messageRepository.save(message);
        MessageDto messageDTO = messageMapper.toDTO(savedMessage);

        sendToKafka(messageDTO);

        log.info("Message sent successfully with ID={}", savedMessage.getId());
        return messageDTO;
    }

    public MessageDto sendSystemMessage(Long recipientId, String subject, String content,
                                        MessageCategory category, MessagePriority priority) {
        log.info("Sending system message to recipientId={}", recipientId);

        Employee recipient = employeeRepository.findById(recipientId)
                .orElseThrow(() -> new EmployeeNotFoundException("Recipient not found: " + recipientId));

        Message message = Message.builder()
                .sender(null)
                .recipient(recipient)
                .subject(subject)
                .content(content)
                .type(MessageType.SYSTEM)
                .category(category)
                .priority(priority)
                .isRead(false)
                .isDeleted(false)
                .build();

        Message savedMessage = messageRepository.save(message);
        MessageDto messageDTO = messageMapper.toDTO(savedMessage);

        sendToKafka(messageDTO);

        log.info("System message sent successfully with ID={}", savedMessage.getId());
        return messageDTO;
    }

    public List<MessageDto> broadcastSystemMessage(String subject, String content,
                                                   MessageCategory category, MessagePriority priority) {
        log.info("Broadcasting system message to all employees");

        List<Employee> allEmployees = employeeRepository.findAll();

        return allEmployees.stream()
                .map(employee -> sendSystemMessage(employee.getId(), subject, content, category, priority))
                .collect(Collectors.toList());
    }

    @Transactional()
    public List<MessageDto> getMessagesForRecipient(Long recipientId) {
        log.info("Fetching messages for recipient: {}", recipientId);
        return messageRepository.findByRecipientIdAndIsDeletedFalseOrderBySentAtDesc(recipientId)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional()
    public List<MessageDto> getUnreadMessages(Long recipientId) {
        log.info("Fetching unread messages for recipient: {}", recipientId);
        return messageRepository.findByRecipientIdAndIsReadFalseAndIsDeletedFalseOrderBySentAtDesc(recipientId)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional()
    public long getUnreadCount(Long recipientId) {
        return messageRepository.countByRecipientIdAndIsReadFalseAndIsDeletedFalse(recipientId);
    }

    @Transactional()
    public List<MessageDto> getMessagesByCategory(Long recipientId, MessageCategory category) {
        log.info("Fetching messages for recipient: {} with category: {}", recipientId, category);
        return messageRepository.findByRecipientIdAndCategoryAndIsDeletedFalseOrderBySentAtDesc(recipientId, category)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional()
    public List<MessageDto> getMessagesBySender(Long senderId) {
        log.info("Fetching messages sent by sender: {}", senderId);
        return messageRepository.findBySenderIdAndIsDeletedFalseOrderBySentAtDesc(senderId)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional()
    public MessageDto getMessageById(Long messageId, Long userId) {
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

    public void markAllAsRead(Long userId) {
        log.info("Marking all messages as read for user {}", userId);
        List<Message> unreadMessages = messageRepository
                .findByRecipientIdAndIsReadFalseAndIsDeletedFalseOrderBySentAtDesc(userId);

        LocalDateTime now = LocalDateTime.now();
        unreadMessages.forEach(message -> {
            message.setIsRead(true);
            message.setReadAt(now);
        });

        messageRepository.saveAll(unreadMessages);
        log.info("Marked {} messages as read for user {}", unreadMessages.size(), userId);
    }

    public void deleteMessage(Long messageId, Long userId) {
        log.info("Deleting message {} for user {}", messageId, userId);
        Message message = messageRepository.findByIdAndRecipientId(messageId, userId)
                .orElseThrow(() -> new EmployeeNotFoundException("Message not found or access denied"));

        message.setIsDeleted(true);
        messageRepository.save(message);

        log.info("Message {} soft-deleted", messageId);
    }

    private void sendToKafka(MessageDto messageDTO) {
        try {
            kafkaTemplate.send(KAFKA_TOPIC,
                    String.valueOf(messageDTO.getRecipientId()),
                    messageDTO);
            log.debug("Message sent to Kafka topic: {}", KAFKA_TOPIC);
        } catch (Exception e) {
            log.error("Failed to send message to Kafka", e);
        }
    }
}