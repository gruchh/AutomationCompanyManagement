// Przykład EmployeeDto
package com.automationcompany.project.model.dto;

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
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String pesel;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private BigDecimal salary;
    private String positionLevel;
    private String department;
    private String employmentType;
    private String status;
    private AddressDto address;
    private BankDetailsDto bankDetails;
    private EmergencyContactDto emergencyContact;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
