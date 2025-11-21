package com.automationcompany.notification.service;

import com.automationcompany.commondomain.dto.NotificationEventDto;
import com.automationcompany.notification.mapper.UserMessageMapper;
import com.automationcompany.notification.model.UserMessage;
import com.automationcompany.notification.repository.UserMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final UserMessageRepository userMessageRepository;
    private final UserMessageMapper userMessageMapper;

    public void saveMessageFromKafka(NotificationEventDto event) {
        UserMessage message = userMessageMapper.toEntity(event);

        userMessageRepository.save(message);
        log.info("Message saved to MongoDB for recipient: {}", event.getRecipientEmail());
    }

    public List<UserMessage> getMessagesForUserByEmail(String email) {
        return userMessageRepository.findByRecipientEmailOrderBySentAtDesc(email);
    }

    public long countUnreadByEmail(String email) {
        return userMessageRepository.countByRecipientEmailAndIsReadFalse(email);
    }

    public void markAsRead(String messageId) {
        userMessageRepository.findById(messageId).ifPresent(msg -> {
            if (!msg.isRead()) {
                msg.setRead(true);
                msg.setReadAt(LocalDateTime.now());
                userMessageRepository.save(msg);
                log.info("Message {} marked as read", messageId);
            }
        });
    }
}