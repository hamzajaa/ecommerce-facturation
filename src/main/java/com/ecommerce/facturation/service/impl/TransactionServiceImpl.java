package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.Credit;
import com.ecommerce.facturation.bean.Debit;
import com.ecommerce.facturation.bean.TransactionCD;
import com.ecommerce.facturation.dao.TransactionDao;
import com.ecommerce.facturation.dto.CreditDTO;
import com.ecommerce.facturation.dto.DebitDTO;
import com.ecommerce.facturation.dto.TransactionDTO;
import com.ecommerce.facturation.mapper.CreditMapper;
import com.ecommerce.facturation.mapper.DebitMapper;
import com.ecommerce.facturation.service.facade.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionDao transactionDao;
    @Autowired
    CreditMapper creditMapper;
    @Autowired
    DebitMapper debitMapper;
    @Override
    public List<TransactionDTO> getTransactions() {
        List<TransactionCD> transactions = transactionDao.findAll();
        List<TransactionDTO> transactionDTOs = transactions.stream().map(transaction ->{
            if (transaction instanceof Credit){
                Credit credit = (Credit) transaction;
                return creditMapper.fromCredit(credit);
            } else if (transaction instanceof Debit) {
                Debit debit = (Debit) transaction;
                return debitMapper.fromDebit(debit);
            }else return null;
        }).collect(Collectors.toList());
        return transactionDTOs;
    }

    @Override
    public TransactionDTO findById(Long id) {
        TransactionCD transaction = transactionDao.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Transaction not found with id :" + id));
        if (transaction instanceof Credit){
            Credit credit = (Credit) transaction;
            return creditMapper.fromCredit(credit);
        } else if (transaction instanceof Debit) {
            Debit debit = (Debit) transaction;
            return debitMapper.fromDebit(debit);
        }else return null;
    }

    @Override
    public CreditDTO saveCredit(CreditDTO creditDTO) {
        return creditMapper.fromCredit(transactionDao.save(creditMapper.fromCreditDTO(creditDTO)));
    }

    @Override
    public DebitDTO saveDebit(DebitDTO debitDTO) {
        return debitMapper.fromDebit(transactionDao.save(debitMapper.fromDebitDTO(debitDTO)));
    }

    @Override
    public CreditDTO updateCredit(CreditDTO creditDTO, Long id) {
        TransactionCD transaction = transactionDao.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Credit not found with id:" + id));
        return saveCredit(creditDTO);
    }

    @Override
    public DebitDTO updateDebit(DebitDTO debitDTO, Long id) {
        TransactionCD transaction = transactionDao.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Debit not found with id:" + id));
        return saveDebit(debitDTO);
    }

    @Override
    public boolean deleteTransactionById(Long id) {
        TransactionCD transaction = transactionDao.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Transaction not found with id:" + id));
        transactionDao.deleteById(id);
        return true;
    }
}
