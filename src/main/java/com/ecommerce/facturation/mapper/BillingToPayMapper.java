package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.Enum.PaymentStatus;
import com.ecommerce.facturation.Enum.TransactionalType;
import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.dto.BillingToPayDTO;
import com.ecommerce.facturation.dto.UserDTO;

import java.math.BigDecimal;

public class BillingToPayMapper {

    public  BillingToPay fromBillingDto(BillingToPayDTO billingToPayDTO)
    {

        BillingToPay billingToPay = new BillingToPay();
        billingToPay.setAmount(billingToPayDTO.amount());
        billingToPay.setPaymentStatus(billingToPay.getPaymentStatus());
        billingToPay.setReason(billingToPayDTO.transactionalType());
      //  billingToPay.setUser(billingToPayDTO.getUser());

        return billingToPay;
    }

    public  BillingToPayDTO fromBilling(BillingToPay billingToPay)
    {

        BillingToPayDTO billingToPayDTO = new BillingToPayDTO(
              billingToPay.getAmount(),
              billingToPay.getPaymentStatus(),
              null,
              billingToPay.getReason()
        );

        return billingToPayDTO;
    }
}
