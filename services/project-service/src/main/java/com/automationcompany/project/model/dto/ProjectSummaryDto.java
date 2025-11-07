package com.automationcompany.project.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Overall summary statistics for all projects in the system. Used in dashboard and reporting endpoints.",
        name = "ProjectSummaryDto"
)
public record ProjectSummaryDto(

        @Schema(
                description = "Total number of projects in the system (regardless of status).",
                example = "47",
                minimum = "0",
                required = true
        )
        Long totalProjects,

        @Schema(
                description = "Total number of projects with 'ACTIVE' or 'IN_PROGRESS' status.",
                example = "23",
                minimum = "0",
                required = true
        )
        Long activeProjects,

        @Schema(
                description = "Total number of projects with 'COMPLETED' status.",
                example = "18",
                minimum = "0",
                required = true
        )
        Long completedProjects,

        @Schema(
                description = "Number of active projects whose planned end date is in the past (potential delays).",
                example = "5",
                minimum = "0",
                required = true
        )
        Long projectsPastDeadline,

        @Schema(
                description = "Average duration of completed projects in days (calculated as endDate - startDate). " +
                        "Returns null if no projects are completed.",
                example = "142.5",
                minimum = "0",
                nullable = true
        )
        Double averageProjectDurationDays

) {}