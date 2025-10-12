package com.automationcompany.employee.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class EmergencyContact {
    private String name;
    private String phone;
    private String relation;
}
