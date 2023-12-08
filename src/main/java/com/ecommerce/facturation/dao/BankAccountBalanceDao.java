package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.bean.BankAccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountBalanceDao extends JpaRepository<BankAccountBalance,Long> {
}
