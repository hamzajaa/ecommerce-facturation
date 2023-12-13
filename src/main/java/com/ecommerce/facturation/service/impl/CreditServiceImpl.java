package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.Credit;
import com.ecommerce.facturation.bean.Debit;
import com.ecommerce.facturation.dao.CreditDao;
import com.ecommerce.facturation.dao.DebitDao;
import com.ecommerce.facturation.dto.CreditDTO;
import com.ecommerce.facturation.mapper.CreditMapper;
import com.ecommerce.facturation.mapper.DebitMapper;
import com.ecommerce.facturation.service.facade.CreditService;

import java.util.List;
import java.util.stream.Collectors;

public class CreditServiceImpl implements CreditService {

    private CreditMapper creditMapper;
    private CreditDao creditDao;


    @Override
    public List<CreditDTO> getCredits() {
        return creditMapper.toDto(creditDao.findAll());
    }

    @Override
    public CreditDTO findById(Long id) {
        return creditMapper.toDto(creditDao.findById(id).get()) ;
    }

    @Override
    public CreditDTO save(CreditDTO creditDTO) {
        Credit credit = creditMapper.toEntity(creditDTO);
        creditDao.save(credit);
        return creditMapper.toDto(credit) ;    }

    @Override
    public CreditDTO update(CreditDTO creditDTO) {
        if(creditDTO.getId() == null) return  null;
        Credit credit = creditDao.findById(creditDTO.getId()).orElseThrow();
        // credit.setAmount();
        //  credit.setSender();
        // credit.setReceiver();
        // credit.setCreatedAt();
        // credit.setBillingsToReceive();
        return creditMapper.toDto(credit);
    }

    @Override
    public boolean deleteCreditById(Long id) {
        creditDao.deleteById(id);
        return findById(id)!= null ? false : true;
    }
}
