package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.PaymentStatus;
import com.ecommerce.facturation.Enum.TransactionalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class TransactionDTO{
    @NotNull(message = "id cannot be null")
    private Long id;
    @NotNull(message = "Full name cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;
    @Valid
    private BankAccountDTO sender;
    @Valid
    private BankAccountDTO receiver;
    private PaymentStatus paymentStatus;
    private TransactionalType transactionalType;
    @Valid
    private InvoiceDTO invoice;
}
