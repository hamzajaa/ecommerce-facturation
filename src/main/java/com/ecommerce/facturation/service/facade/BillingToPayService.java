package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.BillingToPayDTO;

import java.util.List;

public interface BillingToPayService {
    List<BillingToPayDTO> getBillingToPays();
    BillingToPayDTO findById(Long id);
    BillingToPayDTO save(BillingToPayDTO billingToPayDTO);
    BillingToPayDTO update(BillingToPayDTO billingToPayDTO);
    boolean deleteBillingToPayById(Long id);

}
