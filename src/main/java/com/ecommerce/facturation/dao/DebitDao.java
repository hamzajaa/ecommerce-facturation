package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.bean.Debit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitDao extends JpaRepository<Debit,Long> {
}
