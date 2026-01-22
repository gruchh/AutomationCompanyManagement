package com.automationcompany.commondomain.dto;

import com.automationcompany.commondomain.enums.DepartmentType;
import com.automationcompany.commondomain.enums.EmployeeStatus;
import com.automationcompany.commondomain.enums.EmploymentType;
import com.automationcompany.commondomain.enums.PositionLevel;
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
        description = "Data transfer object representing a complete employee record (read-only)",
        name = "EmployeeReadDto"
)
public class EmployeeReadDto {

    @Schema(
            description = "Unique identifier of the employee",
            example = "101"
    )
    private Long id;

    @Schema(
            description = "Employee's first name",
            example = "Jan"
    )
    private String firstName;

    @Schema(
            description = "Employee's last name",
            example = "Kowalski"
    )
    private String lastName;

    @Schema(
            description = "Employee's email address",
            example = "jan.kowalski@company.com"
    )
    private String email;

    @Schema(
            description = "Employee's phone number",
            example = "+48 500 123 456"
    )
    private String phoneNumber;

    @Schema(
            description = "Polish PESEL number (11 digits)",
            example = "90010112345"
    )
    private String pesel;

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
}