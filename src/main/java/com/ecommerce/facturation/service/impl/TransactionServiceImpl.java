package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.Transaction;
import com.ecommerce.facturation.dao.TransactionDao;
import com.ecommerce.facturation.dto.TransactionDto;
import com.ecommerce.facturation.exception.TransactionNotFoundException;
import com.ecommerce.facturation.mapper.TransactionMapper;
import com.ecommerce.facturation.service.facade.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionDao transactionDao;
    @Autowired
    TransactionMapper transactionMapper;

    @Override
    public List<TransactionDto> findAllTransactions() {
        return transactionDao.findAll()
                .stream()
                .map(transaction -> transactionMapper.fromTransaction(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findTransactionById(Long id) {
        Transaction transaction = transactionDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));
        return transactionMapper.fromTransaction(transaction);
    }

    @Override
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.fromTransactionDto(transactionDto);
        Transaction savedTransaction = transactionDao.save(transaction);
        return transactionMapper.fromTransaction(savedTransaction);
    }

    @Override
    public List<TransactionDto> saveTransactions(List<TransactionDto> transactionDtos) {
        return transactionDtos.stream().map(this::saveTransaction).toList();
    }

    @Override
    public TransactionDto updateTransaction(TransactionDto transactionDto) {
        findTransactionById(transactionDto.id());
        Transaction transaction = transactionMapper.fromTransactionDto(transactionDto);
        Transaction updatedTransaction = transactionDao.save(transaction);
        return transactionMapper.fromTransaction(updatedTransaction);
    }

    @Override
    public List<TransactionDto> updateTransactions(List<TransactionDto> transactionDtos) {
        return transactionDtos.stream().map(this::updateTransaction).toList();
    }

    @Override
    public boolean deleteTransaction(Long id) {
        findTransactionById(id);
        transactionDao.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteTransactions(List<TransactionDto> transactionDtos) {
        transactionDtos.stream().map(transactionDto -> deleteTransaction(transactionDto.id())).toList();
        return true;
    }
}
