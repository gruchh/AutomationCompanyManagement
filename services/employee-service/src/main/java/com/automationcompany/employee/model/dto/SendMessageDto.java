package com.automationcompany.employee.model.dto;

import com.automationcompany.commondomain.enums.MessageCategory;
import com.automationcompany.commondomain.enums.MessagePriority;
import com.automationcompany.commondomain.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendMessageDto {
    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull
    private MessageCategory category = MessageCategory.ALERT;
    @NotNull
    private MessagePriority priority = MessagePriority.MEDIUM;
    @NotNull
    private MessageType type = MessageType.SYSTEM;
}