package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.bean.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends JpaRepository<Transaction,Long> {
}
