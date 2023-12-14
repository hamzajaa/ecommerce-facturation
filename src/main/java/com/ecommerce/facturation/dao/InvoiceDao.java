package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.bean.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceDao extends JpaRepository<Invoice, Long> {

    Invoice findByOrderReference(String reference);

    Optional<Invoice> findByOrderId(Long id);
}
