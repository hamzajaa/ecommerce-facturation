package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.BankAccountBalance;
import com.ecommerce.facturation.dto.BankAccountBalanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankAccountBalanceMapper {
    @Autowired
    private BankAccountMapper bankAccountMapper;


    public BankAccountBalance fromBankAccountBalanceDTO(BankAccountBalanceDTO bankAccountBalanceDTO) {
        BankAccountBalance bankAccountBalance = new BankAccountBalance();
        bankAccountBalance.setBalance(bankAccountBalanceDTO.balanceDTO());
        bankAccountBalance.setBankAccount(bankAccountMapper.toEntity(bankAccountBalanceDTO.bankAccountDTO()));
        return bankAccountBalance;
    }
    public BankAccountBalanceDTO fromBankAccountBalance(BankAccountBalance bankAccountBalance) {
        BankAccountBalanceDTO bankAccountBalanceDTO = new BankAccountBalanceDTO(
                bankAccountMapper.toDto(bankAccountBalance.getBankAccount()),
                bankAccountBalance.getBalance()
        );
        return bankAccountBalanceDTO;
    }
}
