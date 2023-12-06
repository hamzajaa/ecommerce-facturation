package com.ecommerce.facturation.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class User extends AbstractSupperClass {
    private String fullname;
    @OneToMany(mappedBy = "user")
    private List<BankAccount> bankAccounts;
    @OneToMany(mappedBy = "user")
    private List<BillingToPay> billingsToPay;
    @OneToMany(mappedBy = "user")
    private List<BillingToReceive> billingsToReceive;
}
