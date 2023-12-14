package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.association.CreditBillingToPay;
import com.ecommerce.facturation.dto.CreditBillingToPayDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Component
@Data
public class CreditBillingToPayMapper extends AbstractMapper<CreditBillingToPay, CreditBillingToPayDTO> {
    @Autowired
    private CreditMapper creditMapper;
    @Autowired
    private BillingToPayMapper billingToReceiveMapper;
    private boolean billingsToPay;

    public void init(boolean value) {
        this.billingsToPay = value;
    }

    public CreditBillingToPayMapper() {
        init(true);
    }

    @Override
    public CreditBillingToPay toEntity(CreditBillingToPayDTO dto) {
        CreditBillingToPay entity = new CreditBillingToPay();
        entity.setId(dto.getId());
        entity.setCredit(creditMapper.toEntity(dto.getCredit()));
        entity.setBillingToPay(billingToReceiveMapper.toEntity(dto.getBillingToPay()));
        return entity;
    }

    @Override
    public CreditBillingToPayDTO toDto(CreditBillingToPay entity) {
        CreditBillingToPayDTO dto = new CreditBillingToPayDTO();
        dto.setId(entity.getId());

        if (this.billingsToPay) {
            dto.setCredit(null);
        } else {
            dto.setCredit(creditMapper.toDto(entity.getCredit()));
        }
        dto.setBillingToPay(billingToReceiveMapper.toDto(entity.getBillingToPay()));
        return dto;
    }
}
