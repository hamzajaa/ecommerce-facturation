package com.ecommerce.facturation.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractSupperClass {
    private String fullName;
    @OneToMany(mappedBy = "user")
    private List<BankAccount> bankAccounts;
    @OneToMany(mappedBy = "user")
    private List<BillingToPay> billingsToPay;
    @OneToMany(mappedBy = "user")
    private List<BillingToReceive> billingsToReceive;
}
