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

        Long orderId,

        String invoiceNumber,

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
        String clientEmail,
        String clientNumberPhone,
        @Valid
        @JsonProperty("products")
        List<CommandItemDto> commandeItemDtos,
        LocalDateTime createAt,
        LocalDateTime updateAt


) {
    public InvoiceDTO(String orderReference,
                      Long orderId,
                      LocalDateTime dueDate,
                      String invoiceNumber,
                      InvoiceStatus invoiceStatus,
                      PaymentMethod paymentMethod,
                      BigDecimal totalPay,
                      String clientName,
                      String clientAddress,
                      String clientEmail,
                      String clientNumberPhone,
                      List<CommandItemDto> commandeItemDtos,
                      LocalDateTime createAt,
                      LocalDateTime updateAt) {
        this(null,
                orderReference,
                orderId,
                invoiceNumber,
                dueDate,
                invoiceStatus,
                paymentMethod,
                totalPay,
                clientName,
                clientAddress,
                clientEmail,
                clientNumberPhone,
                commandeItemDtos,
                createAt,
                updateAt);
    }

    public InvoiceDTO(String orderReference,
                      Long orderId,
                      LocalDateTime dueDate,
                      InvoiceStatus invoiceStatus,
                      PaymentMethod paymentMethod,
                      BigDecimal totalPay,
                      String clientName,
                      String clientAddress,
                      String clientEmail,
                      String clientNumberPhone,
                      List<CommandItemDto> commandeItemDtos) {
        this(null,
                orderReference,
                orderId,
                null,
                dueDate,
                invoiceStatus,
                paymentMethod,
                totalPay,
                clientName,
                clientAddress,
                clientEmail,
                clientNumberPhone,
                commandeItemDtos,
                null,
                null);
    }

}
