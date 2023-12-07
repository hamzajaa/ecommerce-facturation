package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.bean.BillingToReceive;

import java.util.List;

public record UserDTO(
        String fullName,
        List<BankAccountDTO> bankAccountDTOS,
        List<BillingToPayDTO> billingToPayDTOS,
        List<BillingToReceiveDTO> billingToReceiveDTOS
) {

}
