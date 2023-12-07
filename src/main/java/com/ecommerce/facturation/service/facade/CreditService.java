package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.CreditDTO;

import java.util.List;

public interface CreditService {
    List<CreditDTO> getCredits();
    CreditDTO findById(Long id);
    CreditDTO save(CreditDTO creditDTO);
    CreditDTO update(CreditDTO creditDTO);
    boolean deleteCreditById(Long id);

}
