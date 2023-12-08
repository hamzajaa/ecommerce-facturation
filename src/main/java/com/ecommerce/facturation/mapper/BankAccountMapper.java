package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.dto.BankAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper extends AbstractMapper<BankAccount,BankAccountDTO>{

   @Autowired
   private UserMapper userMapper;

    @Override
    public BankAccount toEntity(BankAccountDTO dto) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setRib(dto.ribDTO());
        bankAccount.setBank(dto.bank());
        bankAccount.setUser(userMapper.toEntity(dto.userDto()));
        return bankAccount;
    }

    @Override
    public BankAccountDTO toDto(BankAccount entity) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO(
                entity.getRib(),
                entity.getBank(),
                userMapper.toDto(entity.getUser())
        );
        return bankAccountDTO;
    }
}
