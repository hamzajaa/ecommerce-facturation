package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.Bank;

public record BankAccountDTO(
        Long id,
        String ribDTO,
        Bank bank,
        UserDTO userDto
) {
    public BankAccountDTO( String ribDTO, Bank bank, UserDTO userDto) {
        this(null, ribDTO, bank, userDto);
    }

}
