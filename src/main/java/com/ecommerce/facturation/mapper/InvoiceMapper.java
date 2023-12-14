package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dto.ClientDTO;
import com.ecommerce.facturation.dto.CommandItemDto;
import com.ecommerce.facturation.dto.InvoiceDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceMapper {

    @Autowired
    private JsonMapper jsonMapper;

    public void setJsonMapper(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public Invoice fromInvoiceDto(InvoiceDTO invoiceDTO) {
        if (invoiceDTO == null) return null;
        Invoice invoice = new Invoice();
        invoice.setId(invoiceDTO.invoiceId());
        invoice.setOrderReference(invoiceDTO.orderReference());
        invoice.setOrderId(invoiceDTO.orderId());
        invoice.setInvoiceNumber(invoiceDTO.invoiceNumber());
        invoice.setDueDate(invoiceDTO.dueDate());
        invoice.setInvoiceStatus(invoiceDTO.invoiceStatus());
        invoice.setPaymentMethod(invoiceDTO.paymentMethod());
        invoice.setTotalPay(invoiceDTO.totalPay());
        invoice.setClientName(invoiceDTO.clientName());
        invoice.setClientAddress(invoiceDTO.clientAddress());
        invoice.setClientEmail(invoiceDTO.clientEmail());
        invoice.setClientNumberPhone(invoiceDTO.clientNumberPhone());
        String products = jsonMapper.convertObjectToJson(invoiceDTO.commandeItemDtos());
        invoice.setProducts(products);
        invoice.setCreatedAt(invoiceDTO.createAt());
        invoice.setUpdatedAt(invoiceDTO.updateAt());

        return invoice;
    }

    public InvoiceDTO fromInvoice(Invoice invoice) {
        if (invoice == null) return null;
        List<CommandItemDto> products = jsonMapper.convertJsonToObjects(invoice.getProducts(), CommandItemDto.class);
        InvoiceDTO invoiceDTO = new InvoiceDTO(
                invoice.getId(),
                invoice.getOrderReference(),
                invoice.getOrderId(),
                invoice.getInvoiceNumber(),
                invoice.getDueDate(),
                invoice.getInvoiceStatus(),
                invoice.getPaymentMethod(),
                invoice.getTotalPay(),
                invoice.getClientName(),
                invoice.getClientAddress(),
                invoice.getClientEmail(),
                invoice.getClientNumberPhone(),
                products,
                invoice.getCreatedAt(),
                invoice.getUpdatedAt()
        );

        return invoiceDTO;
    }

}
