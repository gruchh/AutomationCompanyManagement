package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Organizational department")
public enum DepartmentType {

    @Schema(description = "Mechanical engineering team")
    MECHANICAL,

    @Schema(description = "Electrical engineering team")
    ELECTRICAL,

    @Schema(description = "Automation and control systems")
    AUTOMATION,

    @Schema(description = "Software development team")
    SOFTWARE,

    @Schema(description = "Quality assurance and testing")
    QUALITY_ASSURANCE,

    @Schema(description = "Maintenance and technical support")
    MAINTENANCE,

    @Schema(description = "Executive management")
    MANAGEMENT,

    @Schema(description = "Human resources")
    HR,

    @Schema(description = "Finance and accounting")
    FINANCE,

    @Schema(description = "CEO and executive office")
    CEO_OFFICE
}