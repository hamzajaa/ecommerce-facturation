package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.Enum.Role;
import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDTO> getBankAccounts();

    BankAccountDTO findById(Long id) throws BankAccountNotFoundException;

    BankAccountDTO save(BankAccountDTO bankAccountDTO);

    BankAccountDTO update(BankAccountDTO bankAccountDTO);

    boolean deleteBankAccountById(Long id) throws BankAccountNotFoundException;

    BankAccountDTO findByUserEmail(String email);

    void setDataProviderToBankAccount(String payload);

    BankAccountDTO setDataDeliveryManToBankAccount(String payload);
    BankAccount findByUserRole(Role role);

}
