package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactions();
    TransactionDTO findById(Long id);
    TransactionDTO save(TransactionDTO transactionDTO);
    TransactionDTO update(TransactionDTO transactionDTO);
    boolean deleteTransactionById(Long id);

}
