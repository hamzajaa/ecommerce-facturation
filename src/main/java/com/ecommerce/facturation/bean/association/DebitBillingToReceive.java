package com.ecommerce.facturation.bean.association;

import com.ecommerce.facturation.bean.AbstractSupperClass;
import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.bean.Debit;
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
public class DebitBillingToReceive extends AbstractSupperClass {
    @ManyToOne
    private Debit debit;
    @OneToOne
    private BillingToReceive billingToReceive;
}
