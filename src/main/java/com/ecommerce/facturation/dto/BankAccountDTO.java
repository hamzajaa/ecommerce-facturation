package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.Bank;

public record BankAccountDTO(
        String rib,
        Bank bank,
        UserDTO user
) {
}
