package com.automationcompany.project.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Location data transfer object")
public class LocationDto {

    @Schema(description = "Location ID", example = "1")
    private Long id;

    @Schema(description = "Location name", example = "Poznań, Poland")
    private String name;

    @Schema(description = "Latitude", example = "52.406376")
    private Double latitude;

    @Schema(description = "Longitude", example = "16.925167")
    private Double longitude;

    @Schema(description = "Country", example = "Poland")
    private String country;

    @Schema(description = "City", example = "Poznań")
    private String city;
}