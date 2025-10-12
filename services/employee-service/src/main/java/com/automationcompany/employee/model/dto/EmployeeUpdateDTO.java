package com.automationcompany.employee.model.dto;

import com.automationcompany.employee.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUpdateDTO {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Email
    private String email;

    @Size(max = 20)
    private String phoneNumber;

    private LocalDate terminationDate;

    @DecimalMin(value = "0.00")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal salary;

    private PositionLevel positionLevel;

    private DepartmentType department;

    private EmploymentType employmentType;

    private EmployeeStatus status;

    @Valid
    private Address address;

    @Valid
    private BankDetails bankDetails;

    @Valid
    private EmergencyContact emergencyContact;
}
