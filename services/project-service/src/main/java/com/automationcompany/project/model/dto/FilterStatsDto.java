package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Project statistics for filtering options")
public class FilterStatsDto {

    @Schema(
            description = "Number of projects per status",
            example = "{\"ACTIVE\": 15, \"PLANNED\": 8, \"COMPLETED\": 23}"
    )
    private Map<ProjectStatus, Long> statusCounts;

    @Schema(
            description = "Number of projects per service type",
            example = "{\"MACHINE_REALIZATION\": 12, \"MODERNIZATION\": 18}"
    )
    private Map<ProjectServiceType, Long> serviceTypeCounts;

    @Schema(
            description = "Number of projects per location",
            example = "{\"Poznań, Poland\": 10, \"Gdańsk, Poland\": 5}"
    )
    private Map<String, Long> locationCounts;

    @Schema(
            description = "Total number of projects",
            example = "46"
    )
    private Integer totalProjects;
}
