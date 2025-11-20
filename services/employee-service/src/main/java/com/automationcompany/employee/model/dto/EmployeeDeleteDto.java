package com.automationcompany.employee.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "Data transfer object for deleting an employee by ID",
        name = "EmployeeDeleteDto"
)
public class EmployeeDeleteDto {

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be a positive number")
    @Schema(
            description = "Unique identifier of the employee to be deleted",
            example = "42"
    )
    private Long id;
}