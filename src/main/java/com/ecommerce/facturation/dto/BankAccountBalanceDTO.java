package com.ecommerce.facturation.dto;

import java.math.BigDecimal;

public record BankAccountBalanceDTO(

        Long id,
        BankAccountDTO bankAccountDTO,
        BigDecimal balanceDTO
) {

    public BankAccountBalanceDTO(BankAccountDTO bankAccountDTO, BigDecimal balanceDTO) {
        this(null, bankAccountDTO, balanceDTO);
    }
}
