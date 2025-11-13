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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "Public project card – simplified representation used for public listings",
        name = "ProjectCardDto"
)
public class ProjectCardDto {

    @Schema(description = "Project identifier", example = "1001")
    private Long id;

    @Schema(description = "Project name", example = "Production Line Modernization for the Automotive Sector")
    private String name;

    @Schema(description = "Project code (initials/abbreviation)", example = "AC", nullable = true)
    private String code;

    @Schema(description = "Short project description (up to 200 characters)",
            example = "Implementation of modern industrial automation systems")
    private String shortDescription;

    @Schema(description = "Client company name", example = "AutoCorp Poland")
    private String companyName;

    @Schema(description = "Project location", example = "Poznań, Poland")
    private String location;

    @Schema(description = "Project status", example = "IN_PROGRESS", implementation = ProjectStatus.class)
    private ProjectStatus status;

    @Schema(description = "Number of specialists assigned to the project", example = "6")
    private Integer teamSize;

    @Schema(description = "List of technologies/tools used in the project",
            example = "[\"SIEMENS_S7_1500\", \"WINCC_UNIFIED\", \"KUKA\"]")
    private List<ProjectTechnology> technologies;

    @Schema(description = "Project start date", example = "2024-01-15")
    private LocalDate startDate;
}
