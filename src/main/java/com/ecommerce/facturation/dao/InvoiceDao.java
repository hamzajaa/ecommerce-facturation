package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.bean.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDao extends JpaRepository<Invoice,Long> {
}
