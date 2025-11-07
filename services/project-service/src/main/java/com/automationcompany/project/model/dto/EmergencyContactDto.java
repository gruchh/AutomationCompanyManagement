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
        description = "Data transfer object representing an emergency contact person for an employee",
        name = "EmergencyContactDto"
)
public class EmergencyContactDto {

    @NotBlank(message = "Contact name is required")
    @Size(max = 150, message = "Contact name must not exceed 150 characters")
    @Schema(
            description = "Full name of the emergency contact person",
            example = "Anna Kowalska",
            maxLength = 150
    )
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?\\d{9,15}$",
            message = "Phone number must contain 9 to 15 digits, optionally starting with '+'"
    )
    @Schema(
            description = "Emergency contact phone number (e.g., +48123456789 or 123456789)",
            example = "+48 500 123 456",
            pattern = "^\\+?\\d{9,15}$"
    )
    private String phone;

    @NotBlank(message = "Relationship is required")
    @Size(max = 50, message = "Relationship must not exceed 50 characters")
    @Schema(
            description = "Relationship to the employee (e.g., spouse, parent, friend)",
            example = "Małżonka",
            maxLength = 50
    )
    private String relation;
}