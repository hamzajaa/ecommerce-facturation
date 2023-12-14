package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.dto.BillingToPayDTO;
import com.ecommerce.facturation.dto.BillingToReceiveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillingToReceiveMapper extends AbstractMapper<BillingToReceive,BillingToReceiveDTO>{
    @Autowired
    private UserMapper userMapper;

    @Override
    public BillingToReceive toEntity(BillingToReceiveDTO billingToReceiveDTO) {
        BillingToReceive billingToReceive = new BillingToReceive();
        billingToReceive.setAmount(billingToReceiveDTO.amount());
        billingToReceive.setPaymentStatus(billingToReceiveDTO.paymentStatus());
        billingToReceive.setReason(billingToReceiveDTO.transactionalType());
        billingToReceive.setUser(userMapper.toEntity(billingToReceiveDTO.userDTO()));
        return billingToReceive;
    }

    @Override
    public BillingToReceiveDTO toDto(BillingToReceive billingToReceive) {
        BillingToReceiveDTO billingToReceiveDTO = new BillingToReceiveDTO(
                billingToReceive.getAmount(),
                billingToReceive.getPaymentStatus(),
                userMapper.toDto(billingToReceive.getUser()),
                billingToReceive.getReason()
        );

        return billingToReceiveDTO;
    }
}