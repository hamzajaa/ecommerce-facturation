package com.ecommerce.facturation.bean;

import com.ecommerce.facturation.Enum.PaymentStatus;
import com.ecommerce.facturation.bean.association.DebitBillingToReceive;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@DiscriminatorValue("DEBIT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Debit extends TransactionCD {
    @OneToMany
    private Set<DebitBillingToReceive> BillingsToReceive;
}