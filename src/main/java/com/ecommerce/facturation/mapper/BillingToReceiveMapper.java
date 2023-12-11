package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.dto.BillingToPayDTO;
import com.ecommerce.facturation.dto.BillingToReceiveDTO;
import org.springframework.stereotype.Component;

@Component
public class BillingToReceiveMapper extends AbstractMapper<BillingToReceive,BillingToReceiveDTO>{


    @Override
    public BillingToReceive toEntity(BillingToReceiveDTO billingToReceiveDTO) {
        BillingToReceive billingToReceive = new BillingToReceive();
        billingToReceive.setAmount(billingToReceiveDTO.amount());
        billingToReceive.setPaymentStatus(billingToReceiveDTO.paymentStatus());
        billingToReceive.setReason(billingToReceiveDTO.transactionalType());
        //  billingToPay.setUser(billingToPayDTO.getUser());
        return billingToReceive;
    }

    @Override
    public BillingToReceiveDTO toDto(BillingToReceive billingToReceive) {
        BillingToReceiveDTO billingToReceiveDTO = new BillingToReceiveDTO(
                billingToReceive.getAmount(),
                billingToReceive.getPaymentStatus(),
                null,
                billingToReceive.getReason()
        );

        return billingToReceiveDTO;
    }
}