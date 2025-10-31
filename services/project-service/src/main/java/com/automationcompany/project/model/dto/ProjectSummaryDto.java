package com.automationcompany.project.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Overall summary statistics for all projects.")
public record ProjectSummaryDto(
        @Schema(description = "Total number of projects in the system.")
        Long totalProjects,

        @Schema(description = "Total number of projects with 'ACTIVE' status.")
        Long activeProjects,

        @Schema(description = "Total number of projects with 'COMPLETED' status.")
        Long completedProjects,

        @Schema(description = "Number of active projects whose end date is in the past (potential delays).")
        Long projectsPastDeadline,

        @Schema(description = "Average duration of completed projects in days.")
        Double averageProjectDurationDays
) {}