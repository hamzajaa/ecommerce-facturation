package com.ecommerce.facturation.bean;

import com.ecommerce.facturation.bean.association.CreditBillingToPay;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = "credit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credit extends TransactionCD {

    @OneToMany(mappedBy = "credit")
    private List<CreditBillingToPay> BillingsToPay;
}
