package com.ecommerce.facturation.bean.association;

import com.ecommerce.facturation.bean.AbstractSupperClass;
import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.bean.Debit;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class DebitBillingToReceive extends AbstractSupperClass {
    @ManyToOne
    private Debit debit;
    @OneToOne
    private BillingToReceive billingToReceive;
}
