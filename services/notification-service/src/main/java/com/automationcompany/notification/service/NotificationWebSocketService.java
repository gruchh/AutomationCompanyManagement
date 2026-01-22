package com.automationcompany.notification.service;

import com.automationcompany.commondomain.dto.NotificationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendToRecipient(NotificationEventDto event) {
        log.info("Pushing notification via WebSocket to recipient: {}", event.getRecipientEmail());
        messagingTemplate.convertAndSendToUser(
                event.getRecipientEmail(), 
                "/queue/notifications", 
                event
        );
    }
}