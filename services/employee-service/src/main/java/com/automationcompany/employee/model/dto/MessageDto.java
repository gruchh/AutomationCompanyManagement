package com.automationcompany.employee.model.dto;

import com.automationcompany.employee.model.MessageCategory;
import com.automationcompany.employee.model.MessagePriority;
import com.automationcompany.employee.model.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Message data transfer object", name="MessageDto")
public class MessageDto {

    private Long id;
    private Long senderId;
    private String senderName;
    private Long recipientId;
    private String recipientName;
    private String subject;
    private String content;
    private Boolean isRead;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private MessageCategory category;
    private MessagePriority priority;
    private MessageType type;
}
