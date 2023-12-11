package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.bean.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditDao extends JpaRepository<Credit,Long> {
}
