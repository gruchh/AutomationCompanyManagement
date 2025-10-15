package com.automationcompany.employee.model.dto;

import com.automationcompany.employee.model.*;
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
public class EmployeeCreateDTO {

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "PESEL must have 11 digits")
    private String pesel;

    @NotBlank
    @Email
    private String email;

    @Size(max = 20)
    private String phoneNumber;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private LocalDate hireDate;

    @NotNull
    @DecimalMin(value = "0.00")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal salary;

    @NotNull
    private PositionLevel positionLevel;

    @NotNull
    private DepartmentType department;

    @NotNull
    private EmploymentType employmentType;

    private EmployeeStatus status = EmployeeStatus.ACTIVE;

    @Valid
    private Address address;

    @Valid
    private BankDetails bankDetails;

    @Valid
    private EmergencyContact emergencyContact;
}
