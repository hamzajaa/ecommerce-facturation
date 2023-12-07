package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.DebitDTO;

import java.util.List;

public interface DebitService {
    List<DebitDTO> getDebits();
    DebitDTO findById(Long id);
    DebitDTO save(DebitDTO debitDTO);
    DebitDTO update(DebitDTO debitDTO);
    boolean deleteDebitById(Long id);

}
