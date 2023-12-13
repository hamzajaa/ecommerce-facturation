package com.ecommerce.facturation.service.impl;


import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.dao.BillingToReceiveRepo;
import com.ecommerce.facturation.dto.BillingToReceiveDTO;
import com.ecommerce.facturation.mapper.BillingToReceiveMapper;
import com.ecommerce.facturation.service.facade.BillingToReceiveService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingToReceiveServiceImpl implements BillingToReceiveService {
    @Autowired
    private  BillingToReceiveRepo billingToReceiveRepo;
    @Autowired
    private BillingToReceiveMapper billingToReceiveMapper;
    @Override
    public List<BillingToReceiveDTO> getBillingToReceives() {
        return billingToReceiveMapper.toDto(billingToReceiveRepo.findAll());
    }

    @Override
    public BillingToReceiveDTO findById(Long id) {
        BillingToReceive billingToReceive = billingToReceiveRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Billing To Pay not fount with id : "+id));
        return billingToReceiveMapper.toDto(billingToReceive);
    }

    @Override
    public BillingToReceiveDTO save(BillingToReceiveDTO billingToReceiveDTO) {
        BillingToReceive billingToPay =  billingToReceiveMapper.toEntity(billingToReceiveDTO);
        return  billingToReceiveMapper.toDto(billingToReceiveRepo.save(billingToPay));
    }

    @Override
    public BillingToReceiveDTO update(BillingToReceiveDTO billingToReceiveDTO,Long id) {
        BillingToReceive billingToReceive =  billingToReceiveRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Billing To Pay not fount with id : "+id)) ;
        return  billingToReceiveMapper.toDto(billingToReceiveRepo.save(billingToReceive));
    }

    @Override
    public boolean deleteBillingToReceiveById(Long id) {
        billingToReceiveRepo.deleteById(id);
        return  findById(id) != null ? false : true;
    }
}
