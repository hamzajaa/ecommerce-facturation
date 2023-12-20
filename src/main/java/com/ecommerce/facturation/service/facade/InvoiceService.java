package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dto.InvoiceDTO;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;

import java.io.FileNotFoundException;
import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> getInvoices();
    InvoiceDTO findById(Long id);
    InvoiceDTO save(InvoiceDTO invoiceDTO) throws FileNotFoundException, BankAccountNotFoundException;
    InvoiceDTO update(InvoiceDTO invoiceDTO) throws BankAccountNotFoundException;
    boolean deleteInvoiceById(Long id);
    void setDataToInvoice(String payload) throws BankAccountNotFoundException;
    Invoice findByOrderReference(String reference);
    void setDataToInvoiceAndUpdate(String payload) throws BankAccountNotFoundException;
    Invoice findByOrderId(Long id);
}
