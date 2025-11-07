package com.automationcompany.employee.model.dto;

import com.automationcompany.employee.model.MessageCategory;
import com.automationcompany.employee.model.MessagePriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "Data transfer object for sending a new message. Used in message creation endpoints.",
        name = "SendMessageDto"
)
public class SendMessageDto {

    @NotNull(message = "Recipient ID is required")
    @Positive(message = "Recipient ID must be a positive number")
    @Schema(
            description = "ID of the user who will receive the message",
            example = "102",
            required = true
    )
    private Long recipientId;

    @NotBlank(message = "Subject is required")
    @Size(max = 100, message = "Subject cannot exceed 100 characters")
    @Schema(
            description = "Subject line of the message",
            example = "Spotkanie zespołowe",
            maxLength = 100
    )
    private String subject;

    @NotBlank(message = "Content is required")
    @Size(max = 5000, message = "Content cannot exceed 5000 characters")
    @Schema(
            description = "Body content of the message",
            example = "Cześć, zapraszam na spotkanie w piątek o 14:00 w sali B2.",
            maxLength = 5000
    )
    private String content;

    @Builder.Default
    @Schema(
            description = "Category of the message. Defaults to GENERAL if not provided.",
            example = "GENERAL"
    )
    private MessageCategory category = MessageCategory.GENERAL;

    @Builder.Default
    @Schema(
            description = "Priority level of the message. Defaults to MEDIUM if not provided.",
            example = "MEDIUM"
    )
    private MessagePriority priority = MessagePriority.MEDIUM;
}