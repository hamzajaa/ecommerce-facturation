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
        bankAccountBalance.setBankAccount(bankAccountMapper.fromBankAccountDTO(bankAccountBalanceDTO.bankAccountDTO()));
        return bankAccountBalance;
    }
    public BankAccountBalanceDTO fromBankAccountBalance(BankAccountBalance bankAccountBalance) {
        BankAccountBalanceDTO bankAccountBalanceDTO = new BankAccountBalanceDTO(
                bankAccountMapper.fromBankAccount(bankAccountBalance.getBankAccount()),
                bankAccountBalance.getBalance()
        );
        return bankAccountBalanceDTO;
    }
}
