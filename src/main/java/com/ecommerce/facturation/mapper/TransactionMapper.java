package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.Credit;
import com.ecommerce.facturation.bean.Debit;
import com.ecommerce.facturation.bean.TransactionCD;
import com.ecommerce.facturation.dto.CreditDTO;
import com.ecommerce.facturation.dto.DebitDTO;
import com.ecommerce.facturation.dto.TransactionDTO;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {
    public TransactionCD fromTransactionDTO(TransactionDTO transactionDTO){
        return null;
    }
    public TransactionDTO fromTransaction(TransactionCD transactionCD){
        return null;
    }
}
