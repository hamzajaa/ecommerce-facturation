package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.dto.BillingToPayDTO;
import com.ecommerce.facturation.dto.BillingToReceiveDTO;

public class BillingToReceiveMapper {

    public BillingToReceive fromBillingDto(BillingToReceiveDTO billingToReceiveDTO)
    {

        BillingToReceive billingToReceive = new BillingToReceive();
        billingToReceive.setAmount(billingToReceiveDTO.amount());
        billingToReceive.setPaymentStatus(billingToReceiveDTO.paymentStatus());
        billingToReceive.setReason(billingToReceiveDTO.transactionalType());
        //  billingToPay.setUser(billingToPayDTO.getUser());
        return billingToReceive;
    }

    public  BillingToReceiveDTO fromBilling(BillingToReceive billingToReceive)
    {

        BillingToReceiveDTO billingToReceiveDTO = new BillingToReceiveDTO(
                billingToReceive.getAmount(),
                billingToReceive.getPaymentStatus(),
                null,
                billingToReceive.getReason()
        );

        return billingToReceiveDTO;
    }
}