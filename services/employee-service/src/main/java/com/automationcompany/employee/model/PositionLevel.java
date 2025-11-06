package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Career level within the organization")
public enum PositionLevel {

    @Schema(description = "Intern or trainee")
    INTERN,

    @Schema(description = "Entry-level specialist")
    JUNIOR,

    @Schema(description = "Experienced individual contributor")
    MID,

    @Schema(description = "Subject matter expert")
    SENIOR,

    @Schema(description = "Team or technical lead")
    LEAD,

    @Schema(description = "Department manager")
    MANAGER,

    @Schema(description = "Executive director")
    DIRECTOR,

    @Schema(description = "Chief Executive Officer")
    CEO
}