package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BankAccountService {
    List<BankAccountDTO> getBankAccounts();

    BankAccountDTO findById(Long id) throws BankAccountNotFoundException;

    CompletableFuture<BankAccountDTO> save(BankAccountDTO bankAccountDTO);

    BankAccountDTO update(BankAccountDTO bankAccountDTO);

    boolean deleteBankAccountById(Long id) throws BankAccountNotFoundException;

    BankAccountDTO findByUserEmail(String email);

    CompletableFuture<BankAccountDTO> setDataProviderToBankAccount(String payload);
    CompletableFuture<BankAccountDTO> setDataDeliveryManToBankAccount(String payload);
}
