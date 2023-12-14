package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.association.DebitBillingToReceive;
import com.ecommerce.facturation.dto.DebitBillingToReceiveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebitBillingToReceiveMapper extends AbstractMapper<DebitBillingToReceive, DebitBillingToReceiveDTO>{
    @Autowired
    private DebitMapper debitMapper;
    @Autowired
    private BillingToReceiveMapper billingToReceiveMapper;
    @Override
    public DebitBillingToReceive toEntity(DebitBillingToReceiveDTO dto) {
        DebitBillingToReceive entity = new DebitBillingToReceive();
        entity.setId(dto.getId());
        entity.setDebit(debitMapper.toEntity(dto.getDebit()));
        entity.setBillingToReceive(billingToReceiveMapper.toEntity(dto.getBillingToReceive()));
        return entity;
    }

    @Override
    public DebitBillingToReceiveDTO toDto(DebitBillingToReceive entity) {
        DebitBillingToReceiveDTO dto=new DebitBillingToReceiveDTO();
        dto.setId(entity.getId());
        dto.setDebit(debitMapper.toDto(entity.getDebit()));
        dto.setBillingToReceive(billingToReceiveMapper.toDto(entity.getBillingToReceive()));
        return dto;
    }
}
