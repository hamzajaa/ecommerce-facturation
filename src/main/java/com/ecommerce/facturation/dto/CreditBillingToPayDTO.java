package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.Credit;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditBillingToPayDTO {

    private Long id;
    @Valid
    private CreditDTO credit;
    @Valid
    private BillingToPayDTO BillingToPay;
}
