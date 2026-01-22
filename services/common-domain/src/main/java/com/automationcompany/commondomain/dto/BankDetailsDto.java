package com.automationcompany.commondomain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Bank account details for salary payments")
public class BankDetailsDto {

    @Schema(
            description = "Bank account number in IBAN format",
            example = "PL61109010140000071219812874",
            pattern = "^PL\\d{26}$",
            maxLength = 28
    )
    private String bankAccountNumber;

    @Schema(
            description = "Name of the bank",
            example = "PKO Bank Polski",
            maxLength = 100
    )
    private String bankName;

    @Schema(
            description = "Tax identification number (NIP)",
            example = "1234563218",
            pattern = "^\\d{10}$",
            maxLength = 10
    )
    private String taxId;
}