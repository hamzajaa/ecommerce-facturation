package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.bean.User;
import com.ecommerce.facturation.dto.BillingToReceiveDTO;
import com.ecommerce.facturation.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User,UserDTO>{

    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private BillingToPayMapper billingToPayMapper;
    @Autowired
    private BillingToReceiveMapper billingToReceiveMapper;

    @Override
    User toEntity(UserDTO userDTO) {
        if(userDTO==null){
            return null;
        }
        else{
            User user = new User();
            user.setId(userDTO.id());
            user.setFullName(userDTO.fullName());
            user.setEmail(userDTO.email());
            user.setAddress(userDTO.address());
            user.setPhoneNumber(userDTO.phoneNumber());
            user.setRole(userDTO.role());
            //user.setBankAccounts(bankAccountMapper.fromBankAccountDTO(userDTO.bankAccountDTOS()));
            user.setBillingsToPay(billingToPayMapper.toEntity(userDTO.billingToPayDTOS()));
            user.setBillingsToReceive(billingToReceiveMapper.toEntity(userDTO.billingToReceiveDTOS()));
            return user;
        }
    }

    @Override
    UserDTO toDto(User user) {
        if(user==null){
            return null;
        }
        else {
            UserDTO userDTO = new UserDTO(
                    user.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getPhoneNumber(),
                    user.getRole(),
                    //bankAccountMapper.fromBankAccount(user.getBankAccounts()),
                   // billingToPayMapper.toDto(user.getBillingsToPay()),
                    //billingToReceiveMapper.toDto(user.getBillingsToReceive())
            );
            return userDTO;


        }
    }







}
