package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.dao.BillingToPayRepo;
import com.ecommerce.facturation.dao.UserDao;
import com.ecommerce.facturation.dto.BillingToPayDTO;
import com.ecommerce.facturation.exception.BillingNotFoundException;
import com.ecommerce.facturation.exception.ExceptionMessage;
import com.ecommerce.facturation.mapper.BillingToPayMapper;
import com.ecommerce.facturation.service.facade.BillingToPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BellingToPayServiceImpl implements BillingToPayService {

    private  BillingToPayRepo billingToPayRepo;

    private UserDao userDao;

    private BillingToPayMapper billingToPayMapper;
    @Override
    public List<BillingToPayDTO> getBillingToPays() {

        return billingToPayRepo.findAll().stream().map(billingToPayMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BillingToPayDTO findById(Long id) {

        //we don t have no id here to find by
        BillingToPay billingToPay = billingToPayRepo.findById(id).orElseThrow(()-> new BillingNotFoundException());
        return billingToPayMapper.toDto(billingToPay);
    }

    @Override
    public BillingToPayDTO save(BillingToPayDTO billingToPayDTO) {

       BillingToPay billingToPay =  billingToPayMapper.toEntity(billingToPayDTO);
        return  billingToPayMapper.toDto(billingToPayRepo.save(billingToPay));
    }

    @Override
    public BillingToPayDTO update(BillingToPayDTO billingToPayDTO) {

         BillingToPay billingToPay =  billingToPayRepo.findById(0L).get() ;

        // billingToPay.setUser(billingToPayDTO.userDTO()); need mapper user ;
         billingToPay.setAmount(billingToPayDTO.amount());
         billingToPay.setReason(billingToPayDTO.transactionalType());
         billingToPay.setPaymentStatus(billingToPayDTO.paymentStatus());

        return  billingToPayMapper.toDto(billingToPayRepo.save(billingToPay));
    }

    @Override
    public boolean deleteBillingToPayById(Long id) {
        billingToPayRepo.deleteById(id);
        return  findById(id) != null ? false : true;
    }


    @ExceptionHandler(value ={BillingNotFoundException.class})
            public ResponseEntity<ExceptionMessage> handleBillingExeption(Exception exception)
    {
        ExceptionMessage exceptionMessage =
                new ExceptionMessage(exception.getMessage(),LocalDate.now(),HttpStatus.NO_CONTENT);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exceptionMessage);
    }
}
