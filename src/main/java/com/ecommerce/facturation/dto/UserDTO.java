package com.ecommerce.facturation.dto;

import com.ecommerce.facturation.Enum.Role;
import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.bean.BillingToReceive;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    Long id;
    String fullName;
    String email;
    String address;
    String phoneNumber;
    Role role;
    List<BankAccountDTO> bankAccountDTOS;
    List<BillingToPayDTO> billingToPayDTOS;
    List<BillingToReceiveDTO> billingToReceiveDTOS;
}
