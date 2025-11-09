package com.automationcompany.project.model.dto;

import com.automationcompany.commondomain.DepartmentType;
import com.automationcompany.commondomain.EmployeeStatus;
import com.automationcompany.commondomain.EmploymentType;
import com.automationcompany.commondomain.PositionLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "Complete data transfer object representing an employee with all details (read-only response)",
        name = "EmployeeDto"
)
public class EmployeeDto {

    @Schema(
            description = "Unique identifier of the employee",
            example = "101"
    )
    private Long id;

    @Schema(
            description = "Employee's first name",
            example = "John"
    )
    private String firstName;

    @Schema(
            description = "Employee's last name",
            example = "Smith"
    )
    private String lastName;

    @Schema(
            description = "Polish PESEL number (11 digits)",
            example = "90010112345"
    )
    private String pesel;

    @Schema(
            description = "Employee's email address",
            example = "john.smith@company.com"
    )
    private String email;

    @Schema(
            description = "Employee's phone number",
            example = "+48 500 123 456"
    )
    private String phoneNumber;

    @Schema(
            description = "Date of birth of the employee",
            example = "1990-01-01"
    )
    private LocalDate dateOfBirth;

    @Schema(
            description = "Date when the employee was hired",
            example = "2023-06-15"
    )
    private LocalDate hireDate;

    @Schema(
            description = "Termination date (null if still employed)",
            nullable = true
    )
    private LocalDate terminationDate;

    @Schema(
            description = "Monthly gross salary in PLN",
            example = "8500.00"
    )
    private BigDecimal salary;

    @Schema(
            description = "Position level (e.g., JUNIOR, MID, SENIOR)",
            example = "MID",
            implementation = PositionLevel.class
    )
    private PositionLevel positionLevel;

    @Schema(
            description = "Department the employee belongs to",
            example = "IT",
            implementation = DepartmentType.class
    )
    private DepartmentType department;

    @Schema(
            description = "Type of employment contract",
            example = "FULL_TIME",
            implementation = EmploymentType.class
    )
    private EmploymentType employmentType;

    @Schema(
            description = "Current status of the employee",
            example = "ACTIVE",
            implementation = EmployeeStatus.class
    )
    private EmployeeStatus status;

    @Schema(description = "Employee's residential address")
    private AddressDto address;

    @Schema(description = "Bank account details for salary payments")
    private BankDetailsDto bankDetails;

    @Schema(description = "Emergency contact information")
    private EmergencyContactDto emergencyContact;

    @Schema(
            description = "Timestamp when the employee record was created",
            example = "2025-11-07T08:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp when the employee record was last updated",
            example = "2025-11-07T14:22:10"
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "Username or ID of the user who created the record",
            example = "admin"
    )
    private String createdBy;

    @Schema(
            description = "Username or ID of the user who last updated the record",
            example = "hr-manager"
    )
    private String updatedBy;
}