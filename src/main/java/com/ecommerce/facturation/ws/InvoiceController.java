package com.ecommerce.facturation.ws;

import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dto.InvoiceDTO;
import com.ecommerce.facturation.dto.OrderDto;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;
import com.ecommerce.facturation.exception.InvoiceNotFoundException;
import com.ecommerce.facturation.service.impl.InvoiceServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    public InvoiceServiceImpl invoiceService;


    @GetMapping("/")
    public ResponseEntity<List<InvoiceDTO>> findAll() {
        return ResponseEntity.ok(invoiceService.getInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.deleteInvoiceById(id));
    }

    @PostMapping("/")
    public ResponseEntity<InvoiceDTO> save(@Valid @RequestBody InvoiceDTO invoiceDTO) throws BankAccountNotFoundException {
        return new ResponseEntity<>(invoiceService.save(invoiceDTO), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<InvoiceDTO> update(@Valid @RequestBody InvoiceDTO invoiceDTO) throws BankAccountNotFoundException {
        return ResponseEntity.ok(invoiceService.update(invoiceDTO));
    }


//    @GetMapping("/receive_Order/{jsonMessage}")
//    public OrderDto receiveOrder(@PathVariable String jsonMessage) throws JsonProcessingException, ClassNotFoundException {
//        return invoiceService.receiveOrder(jsonMessage);
//    }


}
