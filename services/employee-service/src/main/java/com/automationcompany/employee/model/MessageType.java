package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Typ wiadomości")
public enum MessageType {

    @Schema(description = "Wiadomość systemowa")
    SYSTEM,

    @Schema(description = "Wiadomość od użytkownika")
    USER
}