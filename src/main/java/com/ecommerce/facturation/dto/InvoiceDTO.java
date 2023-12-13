package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.InvoiceStatus;
import com.ecommerce.facturation.Enum.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceDTO(
        Long invoiceId,
        @NotBlank(message = "Order reference cannot be blank")
        String orderReference,
        @Future(message = "Due date must be in the future")
        LocalDateTime dueDate,
        InvoiceStatus invoiceStatus,
        PaymentMethod paymentMethod,
        @NotNull(message = "Total pay cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Total pay must be greater than 0")
        BigDecimal totalPay,
//        @Valid
//        ClientDTO clientDTO,
        String clientName,
        String clientAddress,
        String clientNumberPhone,
        String clientEmail,
        @Valid
        @JsonProperty("products")
        List<CommandItemDto> commandeItemDtos

) {
    public InvoiceDTO(String orderReference,
                      LocalDateTime dueDate,
                      InvoiceStatus invoiceStatus,
                      PaymentMethod paymentMethod,
                      BigDecimal totalPay,
                      String clientName,
                      String clientAddress,
                      String clientNumberPhone,
                      String clientEmail,
                      List<CommandItemDto> commandeItemDtos) {
        this(null,
                orderReference,
                dueDate,
                invoiceStatus,
                paymentMethod,
                totalPay,
                clientName,
                clientAddress,
                clientNumberPhone,
                clientEmail,
                commandeItemDtos);
    }

}
