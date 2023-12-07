package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.Bank;

public record BankAccountDTO(
        String ribDTO,
        Bank bank,
        UserDTO userDto
) {
    public BankAccountDTO{
    }

}
