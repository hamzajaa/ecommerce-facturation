package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.association.CreditBillingToPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingToPayRepo extends JpaRepository<BillingToPay,Long> {
}
