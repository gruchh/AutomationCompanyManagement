package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Schema(description = "Emergency contact person for the employee")
public class EmergencyContact {

    @Schema(description = "Full name of the contact", example = "Anna Kowalska")
    private String name;

    @Schema(description = "Contact phone number", example = "+48500123456")
    private String phone;

    @Schema(description = "Relationship to employee", example = "Spouse")
    private String relation;
}