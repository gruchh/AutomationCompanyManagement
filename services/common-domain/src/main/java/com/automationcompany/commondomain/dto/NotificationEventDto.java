package com.automationcompany.commondomain.dto;

import com.automationcompany.commondomain.enums.MessageCategory;
import com.automationcompany.commondomain.enums.MessagePriority;
import com.automationcompany.commondomain.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEventDto {

    private Long senderId;

    private Long recipientId;

    private String subject;

    private String content;

    private MessageCategory category;
    private MessagePriority priority;
    private MessageType type;

    private LocalDateTime sentAt;
}
