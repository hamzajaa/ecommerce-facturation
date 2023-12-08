package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.BankAccountBalanceDTO;
import com.ecommerce.facturation.exception.BankAccountBalanceNotFoundException;

import java.util.List;

public interface BankAccountBalanceService {
    List<BankAccountBalanceDTO> getBankAccountBalances();
    BankAccountBalanceDTO findById(Long id) throws BankAccountBalanceNotFoundException;
    BankAccountBalanceDTO save(BankAccountBalanceDTO bankAccountBalanceDTO);
    BankAccountBalanceDTO update(BankAccountBalanceDTO bankAccountBalanceDTO);
    boolean deleteBankAccountBalanceById(Long id) throws BankAccountBalanceNotFoundException;

}
