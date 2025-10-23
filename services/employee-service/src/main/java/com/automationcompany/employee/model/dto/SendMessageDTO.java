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

    private Long recipientId;
    private String subject;
    private String content;
}
