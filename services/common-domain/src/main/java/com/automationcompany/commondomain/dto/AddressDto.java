package com.automationcompany.commondomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Address information for an employee")
public class AddressDto {

    @Schema(
            description = "Street name and building number",
            example = "ul. Kwiatowa 15/3",
            maxLength = 255
    )
    private String street;

    @Schema(
            description = "City name",
            example = "Warszawa",
            maxLength = 100
    )
    private String city;

    @Schema(
            description = "Postal code",
            example = "00-001",
            pattern = "^\\d{2}-\\d{3}$",
            maxLength = 6
    )
    private String postalCode;

    @Schema(
            description = "Country name",
            example = "Polska",
            maxLength = 100
    )
    private String country;
}