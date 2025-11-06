package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Message category")
public enum MessageCategory {

    @Schema(description = "Marketing campaign notification")
    CAMPAIGN,

    @Schema(description = "Analytics or report update")
    ANALYTICS,

    @Schema(description = "System alert or warning")
    ALERT,

    @Schema(description = "Budget or financial notification")
    BUDGET,

    @Schema(description = "Task or deadline reminder")
    REMINDER,

    @Schema(description = "New lead or contact")
    LEADS,

    @Schema(description = "Expiring item (contract, certificate, etc.)")
    EXPIRING,

    @Schema(description = "General purpose message")
    GENERAL
}