package com.automationcompany.employee.model;

import com.automationcompany.employee.model.EmployeeStatus;
import com.automationcompany.employee.model.EmploymentType;
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

    @Column
    private LocalDate terminationDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(length = 100)
    private String position;

    @Column(length = 100)
    private String department;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @Column(length = 200)
    private String street;

    @Column(length = 100)
    private String city;

    @Column(length = 10)
    private String postalCode;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String emergencyContactName;

    @Column(length = 20)
    private String emergencyContactPhone;

    @Column(length = 100)
    private String emergencyContactRelation;

    @Column(length = 34)
    private String bankAccountNumber;

    @Column(length = 200)
    private String bankName;

    @Column(length = 20)
    private String taxId;

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