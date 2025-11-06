package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Current employment status of the employee")
public enum EmployeeStatus {

    @Schema(description = "Actively working")
    ACTIVE,

    @Schema(description = "On approved leave (vacation, sick, etc.)")
    ON_LEAVE,

    @Schema(description = "Temporarily suspended")
    SUSPENDED,

    @Schema(description = "Employment terminated")
    TERMINATED
}