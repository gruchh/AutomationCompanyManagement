package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Project data for map display")
public class ProjectMapPointDto {

    @Schema(description = "Project ID", example = "1001")
    private Long id;

    @Schema(description = "Project name", example = "Production Line Modernization")
    private String name;

    @Schema(description = "Project code", example = "AC-2024-001")
    private String code;

    @Schema(description = "Textual location", example = "Poznań, Poland")
    private String location;

    @Schema(description = "Latitude coordinate", example = "52.406376")
    private Double latitude;

    @Schema(description = "Longitude coordinate", example = "16.925167")
    private Double longitude;

    @Schema(description = "Project status")
    private ProjectStatus status;
}