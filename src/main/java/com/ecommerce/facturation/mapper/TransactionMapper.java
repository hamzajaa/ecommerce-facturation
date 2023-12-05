package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.Transaction;
import com.ecommerce.facturation.dto.TransactionDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TransactionMapper {
    public Transaction fromTransactionDto(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.id());
        transaction.setOrderID(transactionDto.orderID());
        transaction.setPrice(transactionDto.price());
        transaction.setPaymentStatus(transactionDto.paymentStatus());
        transaction.setTransactionalType(transactionDto.transactionalType());
        return transaction;
    }

    public TransactionDto fromTransaction(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto(
                transaction.getId(),
                transaction.getOrderID(),
                transaction.getPrice(),
                transaction.getPaymentStatus(),
                transaction.getTransactionalType()
        );
        return transactionDto;
    }
}
