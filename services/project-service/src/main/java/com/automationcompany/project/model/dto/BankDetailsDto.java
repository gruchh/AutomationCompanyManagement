package com.automationcompany.project.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "Data transfer object containing employee's bank account and tax identification details",
        name = "BankDetailsDto"
)
public class BankDetailsDto {

    @NotBlank(message = "Bank account number is required")
    @Pattern(
            regexp = "^\\d{26}$",
            message = "Bank account number must contain exactly 26 digits (IBAN without spaces)"
    )
    @Schema(
            description = "Employee's bank account number (IBAN without spaces, 26 digits for Polish banks)",
            example = "12345678901234567890123456",
            pattern = "\\d{26}"
    )
    private String bankAccountNumber;

    @NotBlank(message = "Bank name is required")
    @Size(max = 150, message = "Bank name must not exceed 150 characters")
    @Schema(
            description = "Name of the bank",
            example = "PKO Bank Polski",
            maxLength = 150
    )
    private String bankName;

    @NotBlank(message = "Tax ID (NIP) is required")
    @Pattern(
            regexp = "^\\d{3}-\\d{3}-\\d{2}-\\d{2}$|^\\d{10}$",
            message = "Tax ID (NIP) must be in format XXX-XXX-XX-XX or 10 digits"
    )
    @Schema(
            description = "Tax Identification Number (NIP) – Polish format: XXX-XXX-XX-XX or 10 digits",
            example = "123-456-78-90",
            pattern = "\\d{3}-\\d{3}-\\d{2}-\\d{2}|\\d{10}"
    )
    private String taxId;
}