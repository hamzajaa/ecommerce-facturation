package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.BillingToReceiveDTO;

import java.util.List;

public interface BillingToReceiveService {
    List<BillingToReceiveDTO> getBillingToReceives();
    BillingToReceiveDTO findById(Long id);
    BillingToReceiveDTO save(BillingToReceiveDTO billingToReceiveDTO);
    BillingToReceiveDTO update(BillingToReceiveDTO billingToReceiveDTO,Long id);
    boolean deleteBillingToReceiveById(Long id);
}
