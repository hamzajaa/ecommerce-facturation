package com.ecommerce.facturation.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DebitBillingToReceiveDTO {
    private Long id;
    @Valid
    private DebitDTO debit;
    @Valid
    private BillingToReceiveDTO billingToReceive;
}
