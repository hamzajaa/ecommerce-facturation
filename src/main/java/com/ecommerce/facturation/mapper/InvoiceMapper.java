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
        invoice.setDueDate(invoiceDTO.dueDate());
        invoice.setInvoiceStatus(invoiceDTO.invoiceStatus());
        invoice.setPaymentMethod(invoiceDTO.paymentMethod());
        invoice.setTotalPay(invoiceDTO.totalPay());
        invoice.setClientName(invoiceDTO.clientName());
        invoice.setClientAddress(invoiceDTO.clientAddress());
        invoice.setClientNumberPhone(invoiceDTO.clientNumberPhone());
        invoice.setClientEmail(invoiceDTO.clientEmail());
        String products = jsonMapper.convertObjectToJson(invoiceDTO.commandeItemDtos());
        invoice.setProducts(products);

        return invoice;
    }

    public InvoiceDTO fromInvoice(Invoice invoice) {
        if (invoice == null) return null;
        List<CommandItemDto> products = jsonMapper.convertJsonToObjects(invoice.getProducts(), CommandItemDto.class);
        InvoiceDTO invoiceDTO = new InvoiceDTO(
                invoice.getId(),
                invoice.getOrderReference(),
                invoice.getDueDate(),
                invoice.getInvoiceStatus(),
                invoice.getPaymentMethod(),
                invoice.getTotalPay(),
                invoice.getClientName(),
                invoice.getClientEmail(),
                invoice.getClientAddress(),
                invoice.getClientNumberPhone(),
                products
        );

        return invoiceDTO;
    }

}
