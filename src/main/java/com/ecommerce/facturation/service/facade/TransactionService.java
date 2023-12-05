package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.bean.Transaction;
import com.ecommerce.facturation.dto.TransactionDto;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    // DTO crud
    List<TransactionDto> findAllTransactions();

    TransactionDto findTransactionById(Long id);

    TransactionDto saveTransaction(TransactionDto transactionDto);

    List<TransactionDto> saveTransactions(List<TransactionDto> transactionDtos);

    TransactionDto updateTransaction(TransactionDto transactionDto);

    List<TransactionDto> updateTransactions(List<TransactionDto> transactionDtos);

    boolean deleteTransaction(Long id);

    boolean deleteTransactions(List<TransactionDto> transactionDtos);
}
