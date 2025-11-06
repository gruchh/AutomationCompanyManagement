package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Employee profile and employment details")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique employee identifier")
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Employee's first name", example = "John")
    private String firstName;

    @Column(nullable = false, length = 100)
    @Schema(description = "Employee's last name", example = "Doe")
    private String lastName;

    @Column(nullable = false, unique = true, length = 11)
    @Schema(description = "Polish PESEL number", example = "90010112345")
    private String pesel;

    @Column(nullable = false, unique = true)
    @Schema(description = "Work email address", example = "john.doe@company.com")
    private String email;

    @Column(length = 20)
    @Schema(description = "Phone number", example = "+48123456789")
    private String phoneNumber;

    @Column(nullable = false)
    @Schema(description = "Date of birth", example = "1990-01-01")
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Schema(description = "Hire date", example = "2020-03-15")
    private LocalDate hireDate;

    @Schema(description = "Termination date (null if still employed)", example = "2025-12-31")
    private LocalDate terminationDate;

    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "Monthly gross salary", example = "8500.00")
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Schema(description = "Position level", implementation = PositionLevel.class)
    private PositionLevel positionLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Schema(description = "Department assignment", implementation = DepartmentType.class)
    private DepartmentType department;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @Schema(description = "Type of employment contract", implementation = EmploymentType.class)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @Schema(description = "Current employment status", implementation = EmployeeStatus.class)
    private EmployeeStatus status;

    @Embedded
    @Schema(description = "Home address details")
    private Address address;

    @Embedded
    @Schema(description = "Bank account details for salary")
    private BankDetails bankDetails;

    @Embedded
    @Schema(description = "Emergency contact information")
    private EmergencyContact emergencyContact;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Schema(description = "Record creation timestamp")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;

    @Column(length = 100)
    @Schema(description = "User who created the record", example = "admin")
    private String createdBy;

    @Column(length = 100)
    @Schema(description = "User who last updated the record", example = "hr_manager")
    private String updatedBy;
}