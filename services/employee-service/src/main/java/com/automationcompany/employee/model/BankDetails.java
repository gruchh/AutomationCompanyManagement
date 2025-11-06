package com.automationcompany.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Schema(description = "Bank account details for salary payments")
public class BankDetails {

    @Schema(description = "IBAN or local bank account number", example = "PL61109010140000071219812874")
    private String bankAccountNumber;

    @Schema(description = "Name of the bank", example = "mBank")
    private String bankName;

    @Schema(description = "Tax identification number (NIP)", example = "525-123-45-67")
    private String taxId;
}