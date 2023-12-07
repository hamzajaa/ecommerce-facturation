package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.BankAccountBalanceDTO;

import java.util.List;

public interface BankAccountBalanceService {
    List<BankAccountBalanceDTO> getBankAccountBalances();
    BankAccountBalanceDTO findById(Long id);
    BankAccountBalanceDTO save(BankAccountBalanceDTO bankAccountBalanceDTO);
    BankAccountBalanceDTO update(BankAccountBalanceDTO bankAccountBalanceDTO);
    boolean deleteBankAccountBalanceById(Long id);

}
