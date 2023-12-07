package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.Debit;
import com.ecommerce.facturation.dto.DebitDTO;
import org.springframework.stereotype.Service;

@Service
public class DebitMapper {
    public Debit fromDebitDTO(DebitDTO debitDTO){
        Debit debit = new Debit();
        debit.setId(debitDTO.getId());
        debit.setAmount(debitDTO.getAmount());
        //debitDTO.getDebitBillingToReceiveDTOS().stream().map();
        //debit.setReceiver(debitDTO.getReceiver());
        //debit.setSender(debitDTO.getSender());
        debit.setPaymentStatus(debitDTO.getPaymentStatus());
        debit.setTransactionalType(debitDTO.getTransactionalType());
        //debit.setInvoice(debitDTO.getInvoiceDTO());
        return debit;
    }

    public DebitDTO fromDebit(Debit debit){
        DebitDTO debitDTO = new DebitDTO();
        debitDTO.setId(debit.getId());
        debitDTO.setAmount(debit.getAmount());
        //debitDTO.setDebitBillingToReceiveDTOS();
        //debitDTO.setReceiver();
        //debitDTO.setSender();
        debitDTO.setPaymentStatus(debit.getPaymentStatus());
        debitDTO.setTransactionalType(debit.getTransactionalType());
        //debitDTO.setInvoiceDTO();
        return debitDTO;
    }
}
