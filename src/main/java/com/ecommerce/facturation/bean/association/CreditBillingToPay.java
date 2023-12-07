package com.ecommerce.facturation.bean.association;

import com.ecommerce.facturation.bean.AbstractSupperClass;
import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.Credit;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditBillingToPay extends AbstractSupperClass {
    @ManyToOne
    private Credit credit;
    @OneToOne
    private BillingToPay billingToPay;
}
