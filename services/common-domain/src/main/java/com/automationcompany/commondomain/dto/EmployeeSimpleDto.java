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
@Schema(description = "Minimal employee data used for inter-service communication")
public class EmployeeSimpleDto {

    @Schema(example = "101")
    private Long id;

    @Schema(example = "Jan")
    private String firstName;

    @Schema(example = "Kowalski")
    private String lastName;

    @Schema(example = "jan.kowalski@company.com")
    private String email;

    @Schema(example = "+48123456789")
    private String phoneNumber;
}