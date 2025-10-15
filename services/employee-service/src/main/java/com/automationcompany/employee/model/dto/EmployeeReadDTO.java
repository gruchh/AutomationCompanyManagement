package com.automationcompany.employee.model.dto;

import com.automationcompany.employee.model.*;
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
public class EmployeeReadDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String pesel;
    private LocalDate dateOfBirth;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private BigDecimal salary;
    private PositionLevel positionLevel;
    private DepartmentType department;
    private EmploymentType employmentType;
    private EmployeeStatus status;

    private Address address;
    private BankDetails bankDetails;
    private EmergencyContact emergencyContact;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
