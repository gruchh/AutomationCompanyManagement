package com.automationcompany.employee.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeKeysDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String publicKey;
    private LocalDateTime createdAt;
    private Boolean isActive;
}