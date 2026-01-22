package com.automationcompany.employee.service.producer;

import com.automationcompany.commondomain.dto.NotificationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    private static final String TOPIC_NAME = "user-message";

    public void sendNotification(NotificationEventDto event) {
        log.info("Sending notification event to Kafka for recipient: {}", event.getRecipientId());
        kafkaTemplate.send(TOPIC_NAME, event);
    }
}