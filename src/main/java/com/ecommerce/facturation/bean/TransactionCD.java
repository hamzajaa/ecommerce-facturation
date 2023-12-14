package com.ecommerce.facturation.bean;

import com.ecommerce.facturation.Enum.PaymentStatus;
import com.ecommerce.facturation.Enum.TransactionalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 255)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TransactionCD extends AbstractSupperClass {
    private BigDecimal amount;
    @ManyToOne
    private BankAccount sender;
    @ManyToOne
    private BankAccount receiver;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private TransactionalType transactionalType;
    @OneToOne
    private Invoice invoice;
}
