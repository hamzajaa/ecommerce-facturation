package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.Credit;
import com.ecommerce.facturation.dto.CreditDTO;
import org.springframework.stereotype.Service;

@Service
public class CreditMapper {
    public Credit fromCreditDTO(CreditDTO creditDTO){
        Credit credit = new Credit();
        credit.setId(creditDTO.getId());
        credit.setAmount(creditDTO.getAmount());
        //credit.setReceiver();
        //credit.setSender();
        //credit.setBillingsToPay();
        credit.setPaymentStatus(creditDTO.getPaymentStatus());
        credit.setTransactionalType(creditDTO.getTransactionalType());
        //credit.setInvoice();
        return credit;
    }

    public CreditDTO fromCredit(Credit credit){
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setId(creditDTO.getId());
        creditDTO.setAmount(creditDTO.getAmount());
        //creditDTO.setCreditBillingToPays();
        //creditDTO.setSender();
        //creditDTO.setReceiver();
        creditDTO.setPaymentStatus(credit.getPaymentStatus());
        creditDTO.setTransactionalType(credit.getTransactionalType());
        //creditDTO.setInvoice(credit.getInvoice());
        return creditDTO;
    }
}
