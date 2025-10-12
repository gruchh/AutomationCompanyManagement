package com.automationcompany.employee.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class BankDetails {
    private String bankAccountNumber;
    private String bankName;
    private String taxId;
}
