package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.Debit;
import com.ecommerce.facturation.dao.DebitDao;
import com.ecommerce.facturation.dao.TransactionDao;
import com.ecommerce.facturation.dto.DebitDTO;
import com.ecommerce.facturation.mapper.DebitMapper;
import com.ecommerce.facturation.service.facade.DebitService;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class DebitServiceImpl implements DebitService {

    private DebitMapper debitMapper;
    private DebitDao debitDao;

    @Override
    public List<DebitDTO> getDebits() {

        return debitDao.findAll().stream().map(debit -> debitMapper.fromDebit(debit)).collect(Collectors.toList());
    }

    @Override
    public DebitDTO findById(Long id) {
        return debitMapper.fromDebit(debitDao.findById(id).get()) ;
    }

    @Override
    public DebitDTO save(DebitDTO debitDTO) {
       Debit debit = debitMapper.fromDebitDTO(debitDTO);
       debitDao.save(debit);
        return debitMapper.fromDebit(debit) ;
    }

    @Override
    public DebitDTO update(DebitDTO debitDTO) {
       if(debitDTO.getId() == null) return  null;
       Debit debit = debitDao.findById(debitDTO.getId()).orElseThrow();
      // debit.setAmount();
      //  debit.setSender();
       // debit.setReceiver();
        // debit.setCreatedAt();
        // debit.setBillingsToReceive();

        return debitMapper.fromDebit(debit);
    }

    @Override
    public boolean deleteDebitById(Long id) {
        debitDao.deleteById(id);
        return findById(id)!= null ? false : true;
    }
}
