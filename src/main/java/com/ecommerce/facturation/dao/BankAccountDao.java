package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.bean.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountDao extends JpaRepository<BankAccount,Long> {

    BankAccount findByUserEmail(String email);
}
