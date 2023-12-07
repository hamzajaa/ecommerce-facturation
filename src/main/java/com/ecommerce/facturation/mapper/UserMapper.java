package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.bean.User;
import com.ecommerce.facturation.dto.BillingToReceiveDTO;
import com.ecommerce.facturation.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /*@Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private BillingToPayMapper bBillingToPayMapper;
    @Autowired
    private BillingToReceiveMapper billingToReceiveMapper;*/


    public User fromUserDto(UserDTO userDTO){
        if(userDTO==null){
            return null;
        }
        else{
            User user = new User();
            user.setId(userDTO.getId());
            user.setFullName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setAddress(userDTO.getAddress());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setRole(userDTO.getRole());
            //user.setBankAccounts(userDTO.bankAccountDTOS());
            //user.setBillingsToPay(userDTO.billingToPayDTOS());
            //user.setBillingsToReceive(userDTO.billingToReceiveDTOS());
            return user;
        }


    }
    public UserDTO fromUser(User user){
        if(user==null){
            return null;
        }
        else {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFullName(user.getFullName());
            userDTO.setEmail(user.getEmail());
            userDTO.setAddress(user.getAddress());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setRole(user.getRole());
            //userDTO.setBankAccountDTOS(user.getBankAccounts());
            //userDTO.setBillingToPayDTOS(user.getBillingsToPay());
            //userDTO.setBillingToReceiveDTOS(user.getBillingsToReceive());
            return userDTO;

        }
    }


}
