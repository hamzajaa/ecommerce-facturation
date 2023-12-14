package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.Enum.InvoiceStatus;
import com.ecommerce.facturation.Enum.PaymentMethod;
import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dao.InvoiceDao;
import com.ecommerce.facturation.dto.*;
import com.ecommerce.facturation.mapper.InvoiceMapper;
import com.ecommerce.facturation.mapper.JsonMapper;
import com.ecommerce.facturation.service.facade.InvoiceService;
import com.ecommerce.facturation.service.facade.TransactionService;
import com.ecommerce.facturation.utils.invoicePdf.GenerateInvoicePdf;
import com.ecommerce.facturation.utils.invoicePdf.SendEmailToClient;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private GenerateInvoicePdf generateInvoicePdf;
    @Autowired
    private SendEmailToClient sendEmailToClient;
    @Autowired
    private JsonMapper jsonMapper;
    @Autowired
    private TransactionService transactionService;

    public void setInvoiceMapper(InvoiceMapper invoiceMapper) {
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public List<InvoiceDTO> getInvoices() {
        log.info("Fetching all invoices.");
        return invoiceDao.findAll().stream().map(invoice -> invoiceMapper.fromInvoice(invoice)).collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findById(Long id) {
        Invoice invoice = invoiceDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found with id: " + id));
        return invoiceMapper.fromInvoice(invoice);
    }

    @Override
//    @Async
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        log.info("Saving a new invoice.");
        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        Invoice savedInvoice = invoiceDao.save(invoice);
//        switch (savedInvoice.getPaymentMethod()) {
//            case ONLINE -> transactionService.saveDebit(new DebitDTO());
//            case ON_DELIVERY -> transactionService.saveCredit(new CreditDTO());
//        }
        try {
            generateInvoicePdf.generate(savedInvoice);
        } catch (FileNotFoundException e) {
            log.error("Error generating PDF for invoice ID: {}", savedInvoice.getId(), e);
            throw new RuntimeException(e);
        }
        sendEmailToClient.send(invoice);
        return invoiceMapper.fromInvoice(savedInvoice);
    }

    //    @Async
    @Override
    @Transactional
    public InvoiceDTO setDataToInvoice(String payload) {
        long startDate = System.currentTimeMillis();
        OrderDto orderDto = jsonMapper.convertJsonToObject(payload, OrderDto.class);
        ClientDTO clientDTO = jsonMapper.convertJsonToObject(orderDto.client(), ClientDTO.class);
        List<CommandItemDto> commandItemDtos = jsonMapper.convertJsonToObjects(orderDto.commandItemDtos(), CommandItemDto.class);
        InvoiceDTO invoiceDTO = new InvoiceDTO(
                orderDto.reference(),
                orderDto.orderId(),
                LocalDateTime.now().plusDays(30),
                orderDto.paymentStatus().equals("EN_ATTENTE") ? InvoiceStatus.PENDING : InvoiceStatus.PAID,
                orderDto.paymentMethod().equals("A_LA_LIVRAISON") ? PaymentMethod.ON_DELIVERY : PaymentMethod.ONLINE,
                orderDto.totalPay(),
                clientDTO.fullName(),
                clientDTO.address(),
                clientDTO.email(),
                clientDTO.phoneNumber(),
                commandItemDtos);
        save(invoiceDTO);
        long endDate = System.currentTimeMillis();
        log.info("Total time {} {}", invoiceDTO.invoiceId(), (endDate - startDate) + "ms");
//        return CompletableFuture.completedFuture(invoiceDTO);
        return invoiceDTO;
    }

    @Override
    public CompletableFuture<InvoiceDTO> setDataToInvoiceUpdate(String payload) {
        long startDate = System.currentTimeMillis();
        OrderDto orderDto = jsonMapper.convertJsonToObject(payload, OrderDto.class);
        Invoice foundedInvoice = findByOrderReference(orderDto.reference());
        foundedInvoice.setInvoiceStatus(InvoiceStatus.PAID);
        InvoiceDTO invoiceDTO = invoiceMapper.fromInvoice(foundedInvoice);
        update(invoiceDTO);
        long endDate = System.currentTimeMillis();
        log.info("Total time {}  {}", invoiceDTO.invoiceId(), (endDate - startDate) + "ms");
        return null;
    }

    @Override
    public Invoice findByOrderReference(String reference) {
        return invoiceDao.findByOrderReference(reference);
    }

    @Override
    @Transactional
    public void setDataToInvoiceAndUpdate(String payload) {
        OrderDto orderDto = jsonMapper.convertJsonToObject(payload, OrderDto.class);
        Invoice foundedInvoice = findByOrderId(orderDto.orderId());
        if (orderDto.paymentStatus().equals("PAYE")) {
            foundedInvoice.setInvoiceStatus(InvoiceStatus.PAID);
        }
        update(invoiceMapper.fromInvoice(foundedInvoice));

    }

    @Override
    public Invoice findByOrderId(Long id) {
        return invoiceDao.findByOrderId(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found with orderId: " + id));
    }

    @Override
    @Transactional
    public InvoiceDTO update(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        Invoice updatedInvoice = invoiceDao.save(invoice);
        try {
            generateInvoicePdf.generateUpdate(updatedInvoice);
            log.info("PDF Invoice generate successfully. ID {}", updatedInvoice.getId());
        } catch (FileNotFoundException e) {
            log.error("Error generating PDF for invoice ID: {}", updatedInvoice.getId(), e);
            throw new RuntimeException(e);
        }
        sendEmailToClient.send(invoice);
        return invoiceMapper.fromInvoice(updatedInvoice);
    }


    @Override
    public boolean deleteInvoiceById(Long id) {
        InvoiceDTO invoiceDTO = findById(id);
        if (invoiceDTO != null) {
            invoiceDao.deleteById(id);
            return true;
        } else return false;
    }


}
