package com.ecommerce.facturation.dto;

import java.math.BigDecimal;

public record BankAccountBalanceDTO(
        BankAccountDTO bankAccountDTO,
        BigDecimal balanceDTO
) {
}
