package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.Enum.Bank;
import com.ecommerce.facturation.Enum.Role;
import com.ecommerce.facturation.bean.BankAccountBalance;
import com.ecommerce.facturation.bean.User;
import com.ecommerce.facturation.dto.BankAccountBalanceDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountBalanceDao extends JpaRepository<BankAccountBalance,Long> {

    BankAccountBalance findByBankAccount_Bank(Bank bank);
    BankAccountBalance findByBankAccount_UserRole(Role role);
}
