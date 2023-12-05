package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.InvoiceStatus;
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
        @NotNull(message = "Total pay cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Total pay must be greater than 0")
        BigDecimal totalPay,
        @Valid
        ClientDTO clientDTO,
        @Valid
        List<CommandItemDto> commandeItemDtos

) {
    public InvoiceDTO(String orderReference,
                      LocalDateTime dueDate,
                      InvoiceStatus invoiceStatus,
                      BigDecimal totalPay,
                      ClientDTO clientDTO,
                      List<CommandItemDto> commandeItemDtos) {
        this(null, orderReference, dueDate, invoiceStatus, totalPay, clientDTO, commandeItemDtos);
    }
}
