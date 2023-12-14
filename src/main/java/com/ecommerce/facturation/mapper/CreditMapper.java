package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.Credit;
import com.ecommerce.facturation.bean.association.CreditBillingToPay;
import com.ecommerce.facturation.dto.CreditDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CreditMapper extends AbstractMapper<Credit,CreditDTO>{
    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private CreditBillingToPayMapper creditBillingToPayMapper;

    @Override
    public Credit toEntity(CreditDTO creditDTO) {
        Credit credit = new Credit();
        credit.setId(creditDTO.getId());
        credit.setAmount(creditDTO.getAmount());
        credit.setReceiver(bankAccountMapper.toEntity(creditDTO.getReceiver()));
        credit.setSender(bankAccountMapper.toEntity(creditDTO.getSender()));

        credit.setBillingsToPay(creditBillingToPayMapper.toEntity(creditDTO.getCreditBillingToPays()));
        credit.setPaymentStatus(creditDTO.getPaymentStatus());
        credit.setTransactionalType(creditDTO.getTransactionalType());
        credit.setInvoice(invoiceMapper.fromInvoiceDto(creditDTO.getInvoice()));
        return credit;
    }

    @Override
    public CreditDTO toDto(Credit credit) {
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setId(credit.getId());
        creditDTO.setAmount(credit.getAmount());
        creditBillingToPayMapper.init(true);
        creditBillingToPayMapper.setBillingsToPay(false);
        creditDTO.setCreditBillingToPays(creditBillingToPayMapper.toDto(credit.getBillingsToPay()));
        creditBillingToPayMapper.setBillingsToPay(true);
        creditDTO.setSender(bankAccountMapper.toDto(credit.getSender()));
        creditDTO.setReceiver(bankAccountMapper.toDto(credit.getReceiver()));
        creditDTO.setPaymentStatus(credit.getPaymentStatus());
        creditDTO.setTransactionalType(credit.getTransactionalType());
        creditDTO.setInvoice(invoiceMapper.fromInvoice(credit.getInvoice()));
        return creditDTO;
    }
}
