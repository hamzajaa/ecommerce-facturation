package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.dto.BillingToReceiveDTO;
import org.springframework.stereotype.Component;

@Component
public class BillingToReceiveMapper extends AbstractMapper<BillingToReceive, BillingToReceiveDTO>{
    @Override
    BillingToReceive toEntity(BillingToReceiveDTO dto) {
        return null;
    }

    @Override
    BillingToReceiveDTO toDto(BillingToReceive entity) {
        return null;
    }
}
