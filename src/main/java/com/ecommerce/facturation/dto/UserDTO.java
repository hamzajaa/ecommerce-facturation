package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.Role;
import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.bean.BillingToReceive;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

public record UserDTO(
        String fullName,
        String email,
        String address,
        String phoneNumber,
        Role role,
        List<BankAccountDTO> bankAccountDTOS,
        List<BillingToPayDTO> billingToPayDTOS,
        List<BillingToReceiveDTO> billingToReceiveDTOS
) {

}
