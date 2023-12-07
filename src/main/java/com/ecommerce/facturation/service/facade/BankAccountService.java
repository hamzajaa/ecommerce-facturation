package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.BankAccountDTO;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDTO> getBankAccounts();
    BankAccountDTO findById(Long id);
    BankAccountDTO save(BankAccountDTO bankAccountDTO);
    BankAccountDTO update(BankAccountDTO bankAccountDTO);
    boolean deleteBankAccountById(Long id);

}
