package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.InvoiceDTO;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface InvoiceService {
    List<InvoiceDTO> getInvoices();
    InvoiceDTO findById(Long id);
    InvoiceDTO save(InvoiceDTO invoiceDTO) throws FileNotFoundException;
    InvoiceDTO update(InvoiceDTO invoiceDTO);
    boolean deleteInvoiceById(Long id);
    CompletableFuture<InvoiceDTO> setDataToInvoice(String payload);
}
