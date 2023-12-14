package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dto.InvoiceDTO;

import java.io.FileNotFoundException;
import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> getInvoices();
    InvoiceDTO findById(Long id);
    InvoiceDTO save(InvoiceDTO invoiceDTO) throws FileNotFoundException;
    InvoiceDTO update(InvoiceDTO invoiceDTO);
    boolean deleteInvoiceById(Long id);
    void setDataToInvoice(String payload);
    Invoice findByOrderReference(String reference);
    void setDataToInvoiceAndUpdate(String payload);
    Invoice findByOrderId(Long id);
}
