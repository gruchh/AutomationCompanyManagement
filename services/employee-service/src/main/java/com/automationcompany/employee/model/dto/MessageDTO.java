package com.automationcompany.employee.model.dto;

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
@Schema(description = "Message data transfer object")
public class MessageDTO {

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
}
