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
@Schema(description = "Emergency contact information for the employee")
public class EmergencyContactDto {

    @Schema(
            description = "Full name of the emergency contact person",
            example = "Anna Kowalska",
            maxLength = 100
    )
    private String name;

    @Schema(
            description = "Phone number of the emergency contact",
            example = "+48 600 789 123",
            maxLength = 20
    )
    private String phone;

    @Schema(
            description = "Relationship to the employee",
            example = "Spouse",
            allowableValues = {"Spouse", "Parent", "Sibling", "Child", "Friend", "Other"},
            maxLength = 50
    )
    private String relation;
}