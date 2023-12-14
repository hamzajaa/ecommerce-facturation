package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.*;
import com.ecommerce.facturation.dao.TransactionDao;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.dto.CreditDTO;
import com.ecommerce.facturation.dto.DebitDTO;
import com.ecommerce.facturation.dto.TransactionDTO;
import com.ecommerce.facturation.mapper.CreditMapper;
import com.ecommerce.facturation.mapper.DebitMapper;
import com.ecommerce.facturation.service.facade.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private CreditMapper creditMapper;
    @Autowired
    private DebitMapper debitMapper;
    @Autowired
    private BankAccountServiceImpl bankAccountService;
    @Override
    public List<TransactionDTO> getTransactions() {
        List<TransactionCD> transactions = transactionDao.findAll();
        List<TransactionDTO> transactionDTOs = transactions.stream().map(transaction ->{
            if (transaction instanceof Credit){
                Credit credit = (Credit) transaction;
                return creditMapper.toDto(credit);
            } else if (transaction instanceof Debit) {
                Debit debit = (Debit) transaction;
                return debitMapper.toDto(debit);
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
            return creditMapper.toDto(credit);
        } else if (transaction instanceof Debit) {
            Debit debit = (Debit) transaction;
            return debitMapper.toDto(debit);
        }else return null;
    }

    @Override
    public CreditDTO saveCredit(CreditDTO creditDTO) {
        Credit save = transactionDao.save(creditMapper.toEntity(creditDTO));
        return creditMapper.toDto(save);
    }

    @Override
    public DebitDTO saveDebit(DebitDTO debitDTO) {
        debitDTO = verifySenderReceiver(debitDTO);
        Debit saveDebit = transactionDao.save(debitMapper.toEntity(debitDTO));
        return debitMapper.toDto(saveDebit);

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
        debitDTO = verifySenderReceiver(debitDTO);
        return saveDebit(debitDTO);
    }

    @Override
    public boolean deleteTransactionById(Long id) {
        TransactionCD transaction = transactionDao.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Transaction not found with id:" + id));
        transactionDao.deleteById(id);
        return true;
    }

    @Override
    public DebitDTO verifySenderReceiver(DebitDTO debitDTO) {
        BankAccountDTO sender = bankAccountService.findById(debitDTO.getSender().id());
        BankAccountDTO receiver = bankAccountService.findById(debitDTO.getReceiver().id());
        debitDTO.setSender(sender);
        debitDTO.setReceiver(receiver);
        return debitDTO;
    }

    @Override
    public BigDecimal SumCreditAmount(List<BillingToPay> billingToPay) {
        // BigDecimal i = BigDecimal.valueOf(0);
        // BigDecimal amount = billingToPay.stream().map(billingToPay1 -> {
        // return i + billingToPay1.getAmount();
        // });
        return null;
    }

    @Override
    public BigDecimal SumDebitAmount(List<BillingToReceive> billingToReceive) {
        return null;
    }

}
