package com.automationcompany.employee.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Recipient employee ID", example = "34", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long recipientId;

    @Schema(description = "Subject of the message", example = "Meeting Reminder", requiredMode = Schema.RequiredMode.REQUIRED)
    private String subject;

    @Schema(description = "Content of the message", example = "Don't forget about the meeting at 10 AM.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;
}
