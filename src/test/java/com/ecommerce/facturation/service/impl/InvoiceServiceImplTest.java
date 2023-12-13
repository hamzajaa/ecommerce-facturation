package com.ecommerce.facturation.service.impl;


import com.ecommerce.facturation.Enum.InvoiceStatus;
import com.ecommerce.facturation.Enum.PaymentMethod;
import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dao.InvoiceDao;
import com.ecommerce.facturation.dto.ClientDTO;
import com.ecommerce.facturation.dto.CommandItemDto;
import com.ecommerce.facturation.dto.InvoiceDTO;
import com.ecommerce.facturation.dto.ProductDto;
import com.ecommerce.facturation.mapper.InvoiceMapper;
import com.ecommerce.facturation.mapper.JsonMapper;
import com.ecommerce.facturation.service.facade.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceImplTest {

    // Mockito will give memory to PropertyService and it will inject this dummy/proxy PropertyService object inside the proxy/dummy object of PropertyController
    @Mock
    private InvoiceDao invoiceDao;
    // Mockito is going to create a proxy/dummy object of PropertyController and inject it to our PropertyControllerTest
    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private JsonMapper jsonMapper = new JsonMapper();
    private InvoiceMapper invoiceMapper = new InvoiceMapper();

    @Test
    public void testFindInvoiceById() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setInvoiceNumber(null);
        invoice.setProducts("[{\"prix\":49.99,\"produit\":{\"Product_id\":8,\"nomProduit\":\"p8\",\"prixProduit\":55.99},\"id\":1,\"quantity\":2},{\"prix\":66.99,\"produit\":{\"Product_id\":6,\"nomProduit\":\"p6\",\"prixProduit\":22.33},\"id\":2,\"quantity\":9},{\"prix\":409.99,\"produit\":{\"Product_id\":1,\"nomProduit\":\"p1\",\"prixProduit\":102.33},\"id\":3,\"quantity\":58}]");

        when(invoiceDao.findById(1L)).thenReturn(Optional.of(invoice));

        invoiceMapper.setJsonMapper(jsonMapper);
        invoiceService.setInvoiceMapper(invoiceMapper);

        InvoiceDTO invoiceDTO = invoiceService.findById(1L);
        Invoice actualInvoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        actualInvoice.setInvoiceNumber(null);
        assertEquals(invoice, actualInvoice);
    }

    @Test
    public void testExceptionForNotFoundCourseById() {
        assertThrows(EntityNotFoundException.class, () -> invoiceService.findById(2L));
    }

    @Test
    public void testCreateInvoice() {


//        when(invoiceDao.findById(any())).thenReturn(Optional.of(invoice));


        invoiceMapper.setJsonMapper(jsonMapper);
        invoiceService.setInvoiceMapper(invoiceMapper);

        invoiceService.save(new InvoiceDTO(1L, "hdd",
                LocalDateTime.now(),
                InvoiceStatus.PENDING, PaymentMethod.ONLINE,
                BigDecimal.ZERO,
                "hamza", "hamza@gmail.com", "marrakech", "+212662356987",
                List.of(
                        new CommandItemDto(1L, 5, BigDecimal.ONE, jsonMapper.convertObjectToJson(new ProductDto(1L, "Product1", BigDecimal.TEN))),
                        new CommandItemDto(2L, 6, BigDecimal.TEN, jsonMapper.convertObjectToJson(new ProductDto(2L, "Product2", BigDecimal.TEN))))
        ));

        verify(invoiceDao).save(any());
    }

    @Test
    public void testUpdateInvoice() {
        InvoiceDTO invoiceDTO = new InvoiceDTO(1L, "hdd",
                LocalDateTime.now(),
                InvoiceStatus.PENDING,PaymentMethod.ONLINE,
                BigDecimal.ZERO,
                "hamza", "hamza@gmail.com", "marrakech", "+212662356987",
                List.of(
                        new CommandItemDto(1L, 5, BigDecimal.ONE, jsonMapper.convertObjectToJson(new ProductDto(1L, "Product1", BigDecimal.TEN))),
                        new CommandItemDto(2L, 6, BigDecimal.TEN, jsonMapper.convertObjectToJson(new ProductDto(2L, "Product2", BigDecimal.TEN))))
        );
//        Invoice invoice = new Invoice();
//        invoice.setId(1L);

//        when(invoiceDao.findById(any())).thenReturn(Optional.of(invoice));

        invoiceMapper.setJsonMapper(jsonMapper);
        invoiceService.setInvoiceMapper(invoiceMapper);

        InvoiceDTO update = invoiceService.update(invoiceDTO);

        ArgumentCaptor<Invoice> argumentCaptor = ArgumentCaptor.forClass(Invoice.class);

        verify(invoiceDao).save(argumentCaptor.capture());

        Invoice captorValue = argumentCaptor.getValue();

        Invoice invoice1 = invoiceMapper.fromInvoiceDto(invoiceDTO);
        invoice1.setInvoiceNumber(null);
        captorValue.setInvoiceNumber(null);

        assertEquals(invoice1, captorValue);

    }

    @Test
    void testFindAll() {
        invoiceService.getInvoices();
        verify(invoiceDao).findAll();
    }

    @Test
    public void deleteInvoiceById() {
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

        when(invoiceDao.findById(1L)).thenReturn(Optional.of(invoice));

        invoiceMapper.setJsonMapper(jsonMapper);
        invoiceService.setInvoiceMapper(invoiceMapper);

        invoiceService.deleteInvoiceById(1L);
        verify(invoiceDao).deleteById(1L);
    }
}
