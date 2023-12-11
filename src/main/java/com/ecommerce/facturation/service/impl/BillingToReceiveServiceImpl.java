package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.dao.BillingToReceiveRepo;
import com.ecommerce.facturation.dto.BillingToReceiveDTO;
import com.ecommerce.facturation.exception.BillingNotFoundException;
import com.ecommerce.facturation.mapper.BillingToReceiveMapper;
import com.ecommerce.facturation.service.facade.BillingToReceiveService;

import java.util.List;
import java.util.stream.Collectors;

public class BillingToReceiveServiceImpl implements BillingToReceiveService {

    private  BillingToReceiveRepo billingToReceiveRepo;
    private BillingToReceiveMapper billingToReceiveMapper;
    @Override
    public List<BillingToReceiveDTO> getBillingToReceives() {

        return billingToReceiveRepo.findAll().stream().map(billingToReceiveMapper::fromBilling).collect(Collectors.toList());
    }

    @Override
    public BillingToReceiveDTO findById(Long id) {
        BillingToReceive billingToReceive = billingToReceiveRepo.findById(id).orElseThrow(()-> new BillingNotFoundException());
        return billingToReceiveMapper.fromBilling(billingToReceive);
    }

    @Override
    public BillingToReceiveDTO save(BillingToReceiveDTO billingToReceiveDTO) {
        BillingToReceive billingToPay =  billingToReceiveMapper.fromBillingDto(billingToReceiveDTO);
        return  billingToReceiveMapper.fromBilling(billingToReceiveRepo.save(billingToPay));
    }

    @Override
    public BillingToReceiveDTO update(BillingToReceiveDTO billingToReceiveDTO) {


        BillingToReceive billingToReceive =  billingToReceiveRepo.findById(1L).get() ;

        // billingToReceive.setUser(billingToReceiveDTO.userDTO()); need mapper user ;
        billingToReceive.setAmount(billingToReceiveDTO.amount());
        billingToReceive.setReason(billingToReceiveDTO.transactionalType());
        billingToReceive.setPaymentStatus(billingToReceiveDTO.paymentStatus());

        return  billingToReceiveMapper.fromBilling(billingToReceiveRepo.save(billingToReceive));

    }

    @Override
    public boolean deleteBillingToReceiveById(Long id) {
        billingToReceiveRepo.deleteById(id);
        return  findById(id) != null ? false : true;
    }
}
