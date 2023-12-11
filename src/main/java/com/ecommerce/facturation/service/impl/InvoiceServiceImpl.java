package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.Enum.InvoiceStatus;
import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dao.InvoiceDao;
import com.ecommerce.facturation.dto.ClientDTO;
import com.ecommerce.facturation.dto.CommandItemDto;
import com.ecommerce.facturation.dto.InvoiceDTO;
import com.ecommerce.facturation.dto.OrderDto;
import com.ecommerce.facturation.mapper.InvoiceMapper;
import com.ecommerce.facturation.mapper.JsonMapper;
import com.ecommerce.facturation.service.facade.InvoiceService;
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
    @Transactional
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        log.info("Saving a new invoice.");
        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        Invoice savedInvoice = invoiceDao.save(invoice);
        try {
            generateInvoicePdf.generate(savedInvoice);
        } catch (FileNotFoundException e) {
            log.error("Error generating PDF for invoice ID: {}", savedInvoice.getId(), e);
            throw new RuntimeException(e);
        }
        sendEmailToClient.send(invoice);
        log.info("Invoice saved and send it successfully. ID: {}", savedInvoice.getId());
        return invoiceMapper.fromInvoice(savedInvoice);
    }
    public void setDataToInvoice(String payload) {
        OrderDto orderDto = jsonMapper.convertJsonToObject(payload, OrderDto.class);
        ClientDTO clientDTO = jsonMapper.convertJsonToObject(orderDto.client(), ClientDTO.class);
        List<CommandItemDto> commandItemDtos = jsonMapper.convertJsonToObjects(orderDto.commandItemDtos(), CommandItemDto.class);
        InvoiceDTO invoiceDTO = new InvoiceDTO(
                orderDto.reference(),
                LocalDateTime.now().plusDays(30),
                InvoiceStatus.Paid,
                orderDto.totalPay(),
                clientDTO.fullName(),
                clientDTO.address(),
                clientDTO.phoneNumber(),
                clientDTO.email(),
                commandItemDtos
        );
        save(invoiceDTO);
    }



    @Override
    public InvoiceDTO update(InvoiceDTO invoiceDTO) {
        findById(invoiceDTO.invoiceId());
        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        Invoice updatedInvoice = invoiceDao.save(invoice);
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
