package com.ecommerce.facturation.controller;

import com.ecommerce.facturation.Enum.InvoiceStatus;
import com.ecommerce.facturation.dto.ClientDTO;
import com.ecommerce.facturation.dto.CommandItemDto;
import com.ecommerce.facturation.dto.InvoiceDTO;
import com.ecommerce.facturation.dto.ProductDto;
import com.ecommerce.facturation.mapper.JsonMapper;
import com.ecommerce.facturation.service.facade.InvoiceService;
import com.ecommerce.facturation.service.impl.InvoiceServiceImpl;
import com.ecommerce.facturation.ws.InvoiceController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Component
public class InvoiceControllerTest {

    @InjectMocks
    private InvoiceController invoiceController;

    @Mock
    private InvoiceServiceImpl invoiceService;
    @Autowired
    private JsonMapper jsonMapper;

    @Test
    @DisplayName("Test Success Scenario for Saving new Invoice")
    public void testSaveInvoice() {

        InvoiceDTO invoiceDTO = new InvoiceDTO("hdd", LocalDateTime.now(),
                InvoiceStatus.Pending, BigDecimal.ZERO,
                new ClientDTO("hamza", "hamza@gmail.com", "marrakech",
                        "+212662356987"),
                List.of(new CommandItemDto(1L, 5, BigDecimal.ONE, jsonMapper.convertObjectToJson(new ProductDto(1L, "Product1", BigDecimal.TEN))), new CommandItemDto(2L, 6, BigDecimal.TEN, jsonMapper.convertObjectToJson(new ProductDto(2L, "Product2", BigDecimal.TEN)))));

        InvoiceDTO savedInvoiceDTO = new InvoiceDTO(1L, invoiceDTO.orderReference(), invoiceDTO.dueDate(), invoiceDTO.invoiceStatus(), invoiceDTO.totalPay(), invoiceDTO.clientDTO(), invoiceDTO.commandeItemDtos());

        when(invoiceService.save(invoiceDTO)).thenReturn(savedInvoiceDTO);

        ResponseEntity<InvoiceDTO> responseEntity = invoiceController.save(invoiceDTO);

        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).invoiceId());

        assertEquals(HttpStatus.CREATED, Objects.requireNonNull(responseEntity.getStatusCode()));

    }

    @Test
    @DisplayName("Test Success Scenario for Fetching All Invoices")
    public void testFindAllInvoices() {
        InvoiceDTO invoiceDTO1 = new InvoiceDTO(1L, "hdd", LocalDateTime.now(), InvoiceStatus.Pending, BigDecimal.ZERO, new ClientDTO("hamza", "hamza@gmail.com", "marrakech", "+212662356987"), List.of(new CommandItemDto(1L, 5, BigDecimal.ONE, jsonMapper.convertObjectToJson(new ProductDto(1L, "Product1", BigDecimal.TEN))), new CommandItemDto(2L, 6, BigDecimal.TEN, jsonMapper.convertObjectToJson(new ProductDto(2L, "Product2", BigDecimal.TEN)))));
        InvoiceDTO invoiceDTO2 = new InvoiceDTO(2L, "hdd", LocalDateTime.now(), InvoiceStatus.Pending, BigDecimal.ZERO, new ClientDTO("hamza", "hamza@gmail.com", "marrakech", "+212662356987"), List.of(new CommandItemDto(1L, 5, BigDecimal.ONE, jsonMapper.convertObjectToJson(new ProductDto(1L, "Product1", BigDecimal.TEN))), new CommandItemDto(2L, 6, BigDecimal.TEN,jsonMapper.convertObjectToJson( new ProductDto(2L, "Product2", BigDecimal.TEN)))));

        List<InvoiceDTO> invoiceDTOList = List.of(invoiceDTO1, invoiceDTO2);

        when(invoiceService.getInvoices()).thenReturn(invoiceDTOList);

        ResponseEntity<List<InvoiceDTO>> responseEntity = invoiceController.findAll();

        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test Success Scenario for updating invoice")
    public void testUpdateInvoice() {
        InvoiceDTO invoiceDTO = new InvoiceDTO(1L, "hdd", LocalDateTime.now(), InvoiceStatus.Pending, BigDecimal.ZERO, new ClientDTO("hamza", "hamza@gmail.com", "marrakech", "+212662356987"), List.of(new CommandItemDto(1L, 5, BigDecimal.ONE,jsonMapper.convertObjectToJson( new ProductDto(1L, "Product1", BigDecimal.TEN))), new CommandItemDto(2L, 6, BigDecimal.TEN, jsonMapper.convertObjectToJson(new ProductDto(2L, "Product2", BigDecimal.TEN)))));

        when(invoiceService.update(any())).thenReturn(invoiceDTO);

        ResponseEntity<InvoiceDTO> responseEntity = invoiceController.update(invoiceDTO);

        assertEquals(invoiceDTO, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    @DisplayName("Test Success Scenario for find invoice by id")
    public void testFindById() {
        InvoiceDTO invoiceDTO = new InvoiceDTO(1L, "hdd", LocalDateTime.now(), InvoiceStatus.Pending, BigDecimal.ZERO, new ClientDTO("hamza", "hamza@gmail.com", "marrakech", "+212662356987"), List.of(new CommandItemDto(1L, 5, BigDecimal.ONE,jsonMapper.convertObjectToJson( new ProductDto(1L, "Product1", BigDecimal.TEN))), new CommandItemDto(2L, 6, BigDecimal.TEN,jsonMapper.convertObjectToJson( new ProductDto(2L, "Product2", BigDecimal.TEN)))));

        when(invoiceService.findById(invoiceDTO.invoiceId())).thenReturn(invoiceDTO);

        ResponseEntity<InvoiceDTO> responseEntity = invoiceController.findById(invoiceDTO.invoiceId());

        assertEquals(invoiceDTO.invoiceId(), Objects.requireNonNull(responseEntity.getBody()).invoiceId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    @DisplayName("Test Success Scenario for delete invoice by id")
    public void testDeleteById() {

        InvoiceDTO invoiceDTO = new InvoiceDTO(1L, "hdd", LocalDateTime.now(), InvoiceStatus.Pending, BigDecimal.ZERO, new ClientDTO("hamza", "hamza@gmail.com", "marrakech", "+212662356987"), List.of(new CommandItemDto(1L, 5, BigDecimal.ONE,jsonMapper.convertObjectToJson( new ProductDto(1L, "Product1", BigDecimal.TEN))), new CommandItemDto(2L, 6, BigDecimal.TEN,jsonMapper.convertObjectToJson( new ProductDto(2L, "Product2", BigDecimal.TEN)))));

        when(invoiceService.deleteInvoiceById(invoiceDTO.invoiceId())).thenReturn(true);

        ResponseEntity<Boolean> responseEntity = invoiceController.deleteById(invoiceDTO.invoiceId());

        assertEquals(true, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


}
