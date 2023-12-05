package com.ecommerce.facturation.dto;



import com.ecommerce.facturation.Enum.PaymentStatus;
import com.ecommerce.facturation.Enum.TransactionalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionDto(
        Long id,
        @NotNull(message = "Order ID cannot be null")
        Long orderID,
        @NotNull(message = "Price cannot be null") @Min(value = 0, message = "Price must be greater than or equal to 0")
        BigDecimal price,
        @NotNull(message = "Payment Status cannot be null")
        //@EnumValue(message = "Payment Status value is invalid")
        PaymentStatus paymentStatus,
        @NotNull(message = "Transactional Type cannot be null")
        //@EnumValue(message = "Transactional Type value is invalid")
        TransactionalType transactionalType
) {

}
