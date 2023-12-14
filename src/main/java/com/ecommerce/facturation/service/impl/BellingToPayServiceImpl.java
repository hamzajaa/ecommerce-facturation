package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.BillingToPay;
import com.ecommerce.facturation.bean.BillingToReceive;
import com.ecommerce.facturation.dao.BillingToPayRepo;
import com.ecommerce.facturation.dao.UserDao;
import com.ecommerce.facturation.dto.BillingToPayDTO;
import com.ecommerce.facturation.exception.BillingNotFoundException;
import com.ecommerce.facturation.exception.ExceptionMessage;
import com.ecommerce.facturation.mapper.BillingToPayMapper;
import com.ecommerce.facturation.mapper.UserMapper;
import com.ecommerce.facturation.service.facade.BillingToPayService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BellingToPayServiceImpl implements BillingToPayService {
    @Autowired
    private  BillingToPayRepo billingToPayRepo;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BillingToPayMapper billingToPayMapper;
    @Override
    public List<BillingToPayDTO> getBillingToPays() {
        return billingToPayMapper.toDto(billingToPayRepo.findAll());
    }

    @Override
    public BillingToPayDTO findById(Long id) {
        BillingToPay billingToPay = billingToPayRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Billing To Pay not fount with id : "+id));
        return billingToPayMapper.toDto(billingToPay);
    }

    @Override
    public BillingToPayDTO save(BillingToPayDTO billingToPayDTO) {
       BillingToPay billingToPay =  billingToPayMapper.toEntity(billingToPayDTO);
        return  billingToPayMapper.toDto(billingToPayRepo.save(billingToPay));
    }

    @Override
    public BillingToPayDTO update(BillingToPayDTO billingToPayDTO,Long id) {
        BillingToPay billingToPay =  billingToPayRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Billing To Pay not fount with id : "+id)) ;
        return  billingToPayMapper.toDto(billingToPayRepo.save(billingToPay));
    }

    @Override
    public boolean deleteBillingToPayById(Long id) {
        billingToPayRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Billing To Pay not fount with id : "+id)) ;
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
