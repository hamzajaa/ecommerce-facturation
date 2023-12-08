package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public BankAccount fromBankAccountDTO(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setRib(bankAccountDTO.ribDTO());
        bankAccount.setBank(bankAccountDTO.bank());
        // bankAccount.setUser(UserMapper.fromUserDTO(bankAccountDTO.UserDto()));
        return bankAccount;
    }


    public BankAccountDTO fromBankAccount(BankAccount bankAccount) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO(
                bankAccount.getRib(),
                bankAccount.getBank(),
                new UserDTO()
        );
        return bankAccountDTO;
    }
}
