package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.PaymentStatus;
import com.ecommerce.facturation.Enum.TransactionalType;

import java.math.BigDecimal;

public record BillingToReceiveDTO(
        BigDecimal amount,
        PaymentStatus paymentStatus,
        UserDTO userDTO,
        TransactionalType transactionalType
) {
}
