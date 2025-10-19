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

    @Schema(description = "Unique message identifier", example = "101")
    private Long id;

    @Schema(description = "Sender employee ID", example = "12")
    private Long senderId;

    @Schema(description = "Full name of the sender", example = "Alice Kowalska")
    private String senderName;

    @Schema(description = "Recipient employee ID", example = "34")
    private Long recipientId;

    @Schema(description = "Full name of the recipient", example = "Jan Nowak")
    private String recipientName;

    @Schema(description = "Subject of the message", example = "Project update")
    private String subject;

    @Schema(description = "Main content of the message", example = "Please review the attached document.")
    private String content;

    @Schema(description = "Whether the message has been read", example = "false")
    private Boolean isRead;

    @Schema(description = "Date and time when the message was sent", example = "2025-10-19T14:30:00")
    private LocalDateTime sentAt;

    @Schema(description = "Date and time when the message was read", example = "2025-10-19T15:00:00")
    private LocalDateTime readAt;
}
