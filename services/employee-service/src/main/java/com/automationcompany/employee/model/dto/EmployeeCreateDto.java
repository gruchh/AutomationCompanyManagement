package com.automationcompany.employee.model.dto;

import com.automationcompany.employee.model.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "Data transfer object for creating a new employee",
        name = "EmployeeCreateDto"
)
public class EmployeeCreateDto {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Schema(
            description = "Employee's first name",
            example = "Jan"
    )
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Schema(
            description = "Employee's last name",
            example = "Kowalski"
    )
    private String lastName;

    @NotBlank(message = "PESEL is required")
    @Pattern(
            regexp = "\\d{11}",
            message = "PESEL must have exactly 11 digits"
    )
    @Schema(
            description = "Polish PESEL number (11 digits)",
            example = "90010112345"
    )
    private String pesel;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(
            description = "Employee's email address",
            example = "jan.kowalski@company.com"
    )
    private String email;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Schema(
            description = "Employee's phone number (optional)",
            example = "+48 123 456 789"
    )
    private String phoneNumber;

    @NotNull(message = "Date of birth is required")
    @Schema(
            description = "Employee's date of birth",
            example = "1990-01-01"
    )
    private LocalDate dateOfBirth;

    @NotNull(message = "Hire date is required")
    @Schema(
            description = "Date when the employee was hired",
            example = "2023-06-15"
    )
    private LocalDate hireDate;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.00", message = "Salary must be >= 0.00")
    @Digits(integer = 8, fraction = 2, message = "Salary must have up to 8 digits in total and 2 fractional digits")
    @Schema(
            description = "Monthly gross salary in PLN",
            example = "8500.00"
    )
    private BigDecimal salary;

    @NotNull(message = "Position level is required")
    @Schema(
            description = "Employee's position level (e.g., JUNIOR, MID, SENIOR)",
            example = "MID"
    )
    private PositionLevel positionLevel;

    @NotNull(message = "Department is required")
    @Schema(
            description = "Department the employee belongs to",
            example = "IT"
    )
    private DepartmentType department;

    @NotNull(message = "Employment type is required")
    @Schema(
            description = "Type of employment contract",
            example = "FULL_TIME"
    )
    private EmploymentType employmentType;

    @Builder.Default
    @Schema(
            description = "Current status of the employee. Defaults to ACTIVE",
            example = "ACTIVE"
    )
    private EmployeeStatus status = EmployeeStatus.ACTIVE;

    @Valid
    @Schema(
            description = "Employee's residential address"
    )
    private Address address;

    @Valid
    @Schema(
            description = "Employee's bank account details for salary payments"
    )
    private BankDetails bankDetails;

    @Valid
    @Schema(
            description = "Emergency contact information"
    )
    private EmergencyContact emergencyContact;
}