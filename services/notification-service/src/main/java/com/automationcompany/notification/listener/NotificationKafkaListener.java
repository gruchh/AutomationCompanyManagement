package com.automationcompany.notification.listener;

import com.automationcompany.commondomain.dto.NotificationEventDto;
import com.automationcompany.notification.service.MessageService;
import com.automationcompany.notification.service.NotificationWebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationKafkaListener {

    private final MessageService messageService;
    private final NotificationWebSocketService webSocketService;

    @KafkaListener(topics = "user-message", groupId = "notification-service-group")
    public void handleUserMessage(NotificationEventDto event) {
        log.info("Received Kafka message for user: {}", event.getRecipientId());
        messageService.saveMessageFromKafka(event);
        webSocketService.sendToRecipient(event);
    }
}