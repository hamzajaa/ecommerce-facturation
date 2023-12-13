package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.Enum.PaymentStatus;
import com.ecommerce.facturation.Enum.TransactionalType;
import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.User;
import com.ecommerce.facturation.dto.BillingToPayDTO;
import com.ecommerce.facturation.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BillingToPayMapper  extends AbstractMapper<BillingToPay,BillingToPayDTO>{
    @Autowired
    private UserMapper userMapper;


    @Override
    public BillingToPay toEntity(BillingToPayDTO billingToPayDTO) {
        BillingToPay billingToPay = new BillingToPay();
        billingToPay.setAmount(billingToPayDTO.amount());
        billingToPay.setPaymentStatus(billingToPay.getPaymentStatus());
        billingToPay.setReason(billingToPayDTO.transactionalType());
        billingToPay.setUser(userMapper.toEntity(billingToPayDTO.userDTO()));
        return billingToPay;
    }

    @Override
    public BillingToPayDTO toDto(BillingToPay billingToPay) {

        BillingToPayDTO billingToPayDTO = new BillingToPayDTO(
                billingToPay.getAmount(),
                billingToPay.getPaymentStatus(),
                userMapper.toDto(billingToPay.getUser()),
                billingToPay.getReason()
        );

        return billingToPayDTO;
    }
}
