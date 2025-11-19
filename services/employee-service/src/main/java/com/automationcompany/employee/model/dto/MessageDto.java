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
@Schema(description = "Data transfer object representing a message", name = "MessageDto")
public class MessageDto {

    @Schema(description = "Unique identifier of the message", example = "201")
    private Long id;

    @Schema(description = "ID of the employee who sent the message", example = "101")
    private Long senderId;

    @Schema(description = "Full name of the sender", example = "Jan Kowalski")
    private String senderName;

    @Schema(description = "ID of the employee who received the message", example = "105")
    private Long recipientId;

    @Schema(description = "Full name of the recipient", example = "Anna Nowak")
    private String recipientName;

    @Schema(description = "Subject line of the message", example = "Project Update")
    private String subject;

    @Schema(description = "Body content of the message", example = "Please review the latest project documents.")
    private String content;

    @Schema(description = "Flag indicating if the message has been read by the recipient", example = "false")
    private Boolean isRead;

    @Schema(description = "Timestamp when the message was sent", example = "2025-11-08T10:00:00")
    private LocalDateTime sentAt;

    @Schema(description = "Timestamp when the message was read (null if unread)", example = "2025-11-08T11:30:00", nullable = true)
    private LocalDateTime readAt;

    @Schema(description = "Category of the message", example = "GENERAL", implementation = MessageCategory.class)
    private MessageCategory category;

    @Schema(description = "Priority level of the message", example = "MEDIUM", implementation = MessagePriority.class)
    private MessagePriority priority;

    @Schema(description = "Type of the message (sent by user or system)", example = "USER", implementation = MessageType.class)
    private MessageType type;
}