package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dto.InvoiceDTO;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    List<InvoiceDTO> getInvoices();
    InvoiceDTO findById(Long id);
    InvoiceDTO save(InvoiceDTO invoiceDTO);
    InvoiceDTO update(InvoiceDTO invoiceDTO);
    boolean deleteInvoiceById(Long id);
    InvoiceDTO save(String payload);
}