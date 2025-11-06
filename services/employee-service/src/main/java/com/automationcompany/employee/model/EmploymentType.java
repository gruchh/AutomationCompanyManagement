package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of employment contract")
public enum EmploymentType {

    @Schema(description = "Full-time employment")
    FULL_TIME,

    @Schema(description = "Part-time employment")
    PART_TIME,

    @Schema(description = "Fixed-term contract")
    CONTRACT,

    @Schema(description = "Temporary or seasonal work")
    TEMPORARY,

    @Schema(description = "Internship or trainee program")
    INTERNSHIP
}