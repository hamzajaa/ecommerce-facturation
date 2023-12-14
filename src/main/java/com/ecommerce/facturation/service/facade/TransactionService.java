package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.dto.CreditDTO;
import com.ecommerce.facturation.dto.DebitDTO;
import com.ecommerce.facturation.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactions();
    TransactionDTO findById(Long id);
    CreditDTO saveCredit(CreditDTO creditDTO);
    DebitDTO saveDebit(DebitDTO debitDTO);
    CreditDTO updateCredit(CreditDTO creditDTO,Long id);
    DebitDTO updateDebit(DebitDTO debitDTO,Long id);
    boolean deleteTransactionById(Long id);
    DebitDTO verifySenderReceiver(DebitDTO debitDTO);
    BigDecimal SumCreditAmount(List<BillingToPay> billingToPay);
    BigDecimal SumDebitAmount(List<BillingToReceive> billingToReceive);


}
