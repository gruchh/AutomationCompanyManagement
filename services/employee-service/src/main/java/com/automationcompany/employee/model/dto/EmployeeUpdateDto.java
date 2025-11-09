package com.automationcompany.employee.model.dto;

import com.automationcompany.commondomain.DepartmentType;
import com.automationcompany.commondomain.EmployeeStatus;
import com.automationcompany.commondomain.EmploymentType;
import com.automationcompany.commondomain.PositionLevel;
import com.automationcompany.employee.model.Address;
import com.automationcompany.employee.model.BankDetails;
import com.automationcompany.employee.model.EmergencyContact;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
        description = "Data transfer object for updating an existing employee. All fields are optional.",
        name = "EmployeeUpdateDto"
)
public class EmployeeUpdateDto {

    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Schema(
            description = "Updated first name of the employee",
            example = "Jan"
    )
    private String firstName;

    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Schema(
            description = "Updated last name of the employee",
            example = "Nowak"
    )
    private String lastName;

    @Email(message = "Invalid email format")
    @Schema(
            description = "Updated email address",
            example = "jan.nowak@company.com"
    )
    private String email;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Schema(
            description = "Updated phone number",
            example = "+48 500 123 456"
    )
    private String phoneNumber;

    @Schema(
            description = "Termination date (null to clear, or set to mark as terminated)",
            example = "2025-12-31",
            nullable = true
    )
    private LocalDate terminationDate;

    @DecimalMin(value = "0.00", message = "Salary must be >= 0.00")
    @Digits(integer = 8, fraction = 2, message = "Salary must have up to 8 digits in total and 2 fractional digits")
    @Schema(
            description = "Updated monthly gross salary in PLN",
            example = "9200.50"
    )
    private BigDecimal salary;

    @Schema(
            description = "Updated position level (e.g., JUNIOR, MID, SENIOR)",
            example = "SENIOR",
            implementation = PositionLevel.class
    )
    private PositionLevel positionLevel;

    @Schema(
            description = "Updated department assignment",
            example = "HR",
            implementation = DepartmentType.class
    )
    private DepartmentType department;

    @Schema(
            description = "Updated employment contract type",
            example = "PART_TIME",
            implementation = EmploymentType.class
    )
    private EmploymentType employmentType;

    @Schema(
            description = "Updated employee status",
            example = "ACTIVE",
            implementation = EmployeeStatus.class
    )
    private EmployeeStatus status;

    @Valid
    @Schema(description = "Updated residential address (fully replaced if provided)")
    private Address address;

    @Valid
    @Schema(description = "Updated bank account details (fully replaced if provided)")
    private BankDetails bankDetails;

    @Valid
    @Schema(description = "Updated emergency contact information (fully replaced if provided)")
    private EmergencyContact emergencyContact;
}