package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.PaymentStatus;
import com.ecommerce.facturation.Enum.TransactionalType;
import com.ecommerce.facturation.bean.association.CreditBillingToPay;
import com.ecommerce.facturation.bean.association.DebitBillingToReceive;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitDTO extends TransactionDTO {
    @Valid
    List<DebitBillingToReceive> debitBillingToReceiveDTOS;

    public DebitDTO(@NotNull(message = "Full name cannot be null") @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0") BigDecimal amount, @Valid BankAccountDTO sender, @Valid BankAccountDTO receiver, PaymentStatus paymentStatus, TransactionalType transactionalType, @Valid InvoiceDTO invoice, List<DebitBillingToReceive> debitBillingToReceiveDTOS) {
        super(null, amount, sender, receiver, paymentStatus, transactionalType, invoice);
    }
}
