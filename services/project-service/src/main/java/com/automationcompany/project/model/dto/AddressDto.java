package com.automationcompany.project.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "Data transfer object representing a physical address",
        name = "AddressDto"
)
public class AddressDto {

    @NotBlank(message = "Street is required")
    @Size(max = 150, message = "Street must not exceed 150 characters")
    @Schema(
            description = "Street name and number (e.g., Main St 123)",
            example = "Marszałkowska 10/15",
            maxLength = 150
    )
    private String street;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    @Schema(
            description = "City or town name",
            example = "Warszawa",
            maxLength = 100
    )
    private String city;

    @NotBlank(message = "Postal code is required")
    @Pattern(
            regexp = "^\\d{2}-\\d{3}$",
            message = "Postal code must follow format: XX-XXX (e.g., 00-001)"
    )
    @Schema(
            description = "Postal code in format XX-XXX",
            example = "00-001",
            pattern = "\\d{2}-\\d{3}"
    )
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    @Schema(
            description = "Country name",
            example = "Polska",
            maxLength = 100
    )
    private String country;
}