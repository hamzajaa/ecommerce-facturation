package com.ecommerce.facturation.bean;

import com.ecommerce.facturation.Enum.PaymentStatus;
import com.ecommerce.facturation.Enum.TransactionalType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingToPay extends AbstractSupperClass {
    private BigDecimal amount;
    @Enumerated
    private PaymentStatus paymentStatus;
    @ManyToOne
    private User user;
    @Enumerated
    private TransactionalType reason;
}
