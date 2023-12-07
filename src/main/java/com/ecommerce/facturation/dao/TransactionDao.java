package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.bean.TransactionCD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<TransactionCD,Long> {
}
