package com.automationcompany.employee.model;

import com.automationcompany.commondomain.enums.DepartmentType;
import com.automationcompany.commondomain.enums.EmployeeStatus;
import com.automationcompany.commondomain.enums.EmploymentType;
import com.automationcompany.commondomain.enums.PositionLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 11)
    private String pesel;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private LocalDate hireDate;

    private LocalDate terminationDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PositionLevel positionLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DepartmentType department;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EmployeeStatus status;

    @Embedded
    private Address address;

    @Embedded
    private BankDetails bankDetails;

    @Embedded
    private EmergencyContact emergencyContact;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(length = 100)
    private String createdBy;

    @Column(length = 100)
    private String updatedBy;
}