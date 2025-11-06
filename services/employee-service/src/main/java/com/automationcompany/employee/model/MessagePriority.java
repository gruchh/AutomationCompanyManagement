package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Message priority level")
public enum MessagePriority {

    @Schema(description = "Low priority")
    LOW,

    @Schema(description = "Medium priority")
    MEDIUM,

    @Schema(description = "High priority")
    HIGH
}