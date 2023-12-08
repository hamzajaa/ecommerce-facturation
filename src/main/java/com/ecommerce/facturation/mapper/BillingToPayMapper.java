package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.dto.BillingToPayDTO;
import org.springframework.stereotype.Component;

@Component
public class BillingToPayMapper extends AbstractMapper<BillingToPay, BillingToPayDTO>{
    @Override
    BillingToPay toEntity(BillingToPayDTO billingToPayDTO) {
        return null;
    }

    @Override
    BillingToPayDTO toDto(BillingToPay billingToPay) {
        return null;
    }
}
