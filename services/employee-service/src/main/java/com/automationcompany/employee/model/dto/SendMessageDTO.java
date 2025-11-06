package com.automationcompany.employee.model.dto;

import com.automationcompany.employee.model.MessageCategory;
import com.automationcompany.employee.model.MessagePriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data required to send a message")
public class SendMessageDTO {
    @NotNull(message = "Recipient ID is required")
    private Long recipientId;

    @NotBlank(message = "Subject is required")
    @Size(max = 100, message = "Subject cannot exceed 100 characters")
    private String subject;

    @NotBlank(message = "Content is required")
    private String content;

    private MessageCategory category = MessageCategory.GENERAL;
    private MessagePriority priority = MessagePriority.MEDIUM;
}
