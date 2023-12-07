package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.PaymentStatus;
import com.ecommerce.facturation.Enum.TransactionalType;
import com.ecommerce.facturation.bean.association.CreditBillingToPay;
import com.ecommerce.facturation.bean.association.DebitBillingToReceive;

import java.math.BigDecimal;
import java.util.List;

public record DebitDTO(

        BigDecimal amount,
        BankAccountDTO sender,
        BankAccountDTO receiver,
        PaymentStatus paymentStatus,
        TransactionalType transactionalType,
        InvoiceDTO invoiceDTO,
        List<DebitBillingToReceive> debitBillingToReceives
) {
}
