package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Schema(description = "Employee's residential address")
public class Address {

    @Schema(description = "Street name and number", example = "123 Main Street")
    private String street;

    @Schema(description = "City", example = "Warsaw")
    private String city;

    @Schema(description = "Postal code", example = "00-950")
    private String postalCode;

    @Schema(description = "Country", example = "Poland")
    private String country;
}