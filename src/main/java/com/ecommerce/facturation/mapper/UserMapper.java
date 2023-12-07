package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.bean.User;
import com.ecommerce.facturation.dto.UserDTO;

public class UserMapper {



    public User fromUserDto(UserDTO userDTO){
        if(userDTO==null){
            return null;
        }
        else{
            User user = new User();

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
