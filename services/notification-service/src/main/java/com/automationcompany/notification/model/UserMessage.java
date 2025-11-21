package com.automationcompany.notification.model;

import com.automationcompany.commondomain.enums.MessageCategory;
import com.automationcompany.commondomain.enums.MessagePriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user_messages")
public class UserMessage {

    @Id
    private String id;

    private Long senderId;
    private Long recipientId;
    private String recipientEmail;
    private String subject;
    private String content;
    private MessageCategory category;
    private MessagePriority priority;
    private LocalDateTime sentAt;

    @Builder.Default
    private boolean isRead = false;
    private LocalDateTime readAt;
}