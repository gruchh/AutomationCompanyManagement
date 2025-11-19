package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.ProjectTechnology;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "Public project card – simplified representation for project listings",
        name = "ProjectCardDto"
)
public class ProjectCardDto {

    @Schema(description = "Project identifier", example = "1001")
    private Long id;

    @Schema(description = "Project name", example = "Production Line Modernization")
    private String name;

    @Schema(
            description = "Location name",
            example = "Poznań, Poland",
            nullable = true
    )
    private String location;

    @Schema(description = "Project status", example = "IN_PROGRESS")
    private ProjectStatus status;

    @Schema(
            description = "Number of specialists assigned to the project",
            example = "6",
            nullable = true
    )
    private Integer teamSize;

    @Schema(
            description = "List of technologies/tools used in the project",
            example = "[\"SIEMENS_S7_1500\", \"WINCC_UNIFIED\", \"KUKA\"]",
            nullable = true
    )
    private List<ProjectTechnology> technologies;

    @Schema(
            description = "Project start date",
            example = "2024-01-15"
    )
    private LocalDate startDate;
}