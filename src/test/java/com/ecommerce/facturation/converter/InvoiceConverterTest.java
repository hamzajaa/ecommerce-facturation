package com.ecommerce.facturation.converter;

import com.ecommerce.facturation.Enum.InvoiceStatus;
import com.ecommerce.facturation.Enum.PaymentMethod;
import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dto.ClientDTO;
import com.ecommerce.facturation.dto.CommandItemDto;
import com.ecommerce.facturation.dto.InvoiceDTO;
import com.ecommerce.facturation.dto.ProductDto;
import com.ecommerce.facturation.mapper.InvoiceMapper;
import com.ecommerce.facturation.mapper.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@Component
public class InvoiceConverterTest {

    @InjectMocks
    private InvoiceMapper invoiceMapper;

    private JsonMapper jsonMapper = new JsonMapper();

    @Test
    void testConvertDTOtoEntity_Success() {
        InvoiceDTO invoiceDTO = new InvoiceDTO(1L, "hdd",
                LocalDateTime.now(),
                InvoiceStatus.PENDING, PaymentMethod.ONLINE,
                BigDecimal.ZERO,
                "hamza", "hamza@gmail.com", "marrakech", "+212662356987",
                List.of(
                        new CommandItemDto(1L, 5, BigDecimal.ONE,jsonMapper.convertObjectToJson( new ProductDto(1L, "Product1", BigDecimal.TEN))),
                        new CommandItemDto(2L, 6, BigDecimal.TEN, jsonMapper.convertObjectToJson(new ProductDto(2L, "Product2", BigDecimal.TEN))))
        );

        invoiceMapper.setJsonMapper(jsonMapper);

        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        InvoiceDTO invoiceDTO1 = invoiceMapper.fromInvoice(invoice);
        invoice.setInvoiceNumber(null);
        assertEquals(invoiceDTO, invoiceDTO1);

    }

    @Test
    void testConvertEntityToDTO_Success() throws JsonProcessingException {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setOrderReference("hhhh");
        invoice.setInvoiceStatus(InvoiceStatus.PAID);
        invoice.setTotalPay(BigDecimal.valueOf(155.22));
        invoice.setClientName("hamza");
        invoice.setClientAddress("marrakech");
        invoice.setClientNumberPhone("0664230265");
        invoice.setClientEmail("hamza@gmail.com");
        invoice.setProducts("[{\"prix\":49.99,\"produit\":{\"Product_id\":8,\"nomProduit\":\"p8\",\"prixProduit\":55.99},\"id\":1,\"quantity\":2},{\"prix\":66.99,\"produit\":{\"Product_id\":6,\"nomProduit\":\"p6\",\"prixProduit\":22.33},\"id\":2,\"quantity\":9},{\"prix\":409.99,\"produit\":{\"Product_id\":1,\"nomProduit\":\"p1\",\"prixProduit\":102.33},\"id\":3,\"quantity\":58}]");

        invoiceMapper.setJsonMapper(jsonMapper);

        InvoiceDTO invoiceDTO = invoiceMapper.fromInvoice(invoice);
        invoice.setInvoiceNumber(null);
        ObjectMapper objectMapper = new ObjectMapper();
        assertEquals(objectMapper.writeValueAsString(invoiceDTO.commandeItemDtos()), invoice.getProducts());
        assertEquals(invoiceDTO.invoiceStatus(), invoice.getInvoiceStatus());

    }


}
