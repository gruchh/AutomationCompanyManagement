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
@Schema(description = "Dane projektu do wyświetlenia na mapie")
public class ProjectMapPointDto {

    @Schema(description = "ID projektu", example = "1001")
    private Long id;

    @Schema(description = "Nazwa projektu", example = "Modernizacja Linii Produkcyjnej")
    private String name;

    @Schema(description = "Kod projektu", example = "AC")
    private String code;

    @Schema(description = "Lokalizacja tekstowa", example = "Poznań, Polska")
    private String location;

    @Schema(description = "Szerokość geograficzna", example = "52.406376")
    private Double latitude;

    @Schema(description = "Długość geograficzna", example = "16.925167")
    private Double longitude;

    @Schema(description = "Status projektu")
    private ProjectStatus status;

    @Schema(description = "Etykieta statusu", example = "W toku")
    private String statusLabel;

    @Schema(description = "Liczba specjalistów", example = "6")
    private Integer teamSize;

    @Schema(description = "Kolor pinezki na mapie", example = "#6366f1")
    private String markerColor;
}