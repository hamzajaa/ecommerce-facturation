package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.Debit;
import com.ecommerce.facturation.dto.DebitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebitMapper extends AbstractMapper<Debit,DebitDTO>{
    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private DebitBillingToReceiveMapper debitBillingToReceiveMapper;

    @Override
    public Debit toEntity(DebitDTO debitDTO) {
        Debit debit = new Debit();
        debit.setId(debitDTO.getId());
        debit.setAmount(debitDTO.getAmount());
        debit.setDebitBillingsToReceive(debitBillingToReceiveMapper.toEntity(debitDTO.getDebitBillingToReceiveDTOS()));
        debit.setReceiver(bankAccountMapper.toEntity(debitDTO.getReceiver()));
        debit.setSender(bankAccountMapper.toEntity(debitDTO.getSender()));
        debit.setPaymentStatus(debitDTO.getPaymentStatus());
        debit.setTransactionalType(debitDTO.getTransactionalType());
        debit.setInvoice(invoiceMapper.fromInvoiceDto(debitDTO.getInvoice()));
        return debit;
    }

    @Override
    public DebitDTO toDto(Debit debit) {
        DebitDTO debitDTO = new DebitDTO();
        debitDTO.setId(debit.getId());
        debitDTO.setAmount(debit.getAmount());
        debitDTO.setDebitBillingToReceiveDTOS(debitBillingToReceiveMapper.toDto(debit.getDebitBillingsToReceive()));
        debitDTO.setReceiver(bankAccountMapper.toDto(debit.getReceiver()));
        debitDTO.setSender(bankAccountMapper.toDto(debit.getSender()));
        debitDTO.setPaymentStatus(debit.getPaymentStatus());
        debitDTO.setTransactionalType(debit.getTransactionalType());
        debitDTO.setInvoice(invoiceMapper.fromInvoice(debit.getInvoice()));
        return debitDTO;    }
}
