package com.ecommerce.facturation.bean;

import com.ecommerce.facturation.Enum.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private String email;
    private String address;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<BankAccount> bankAccounts;
    @OneToMany(mappedBy = "user")
    private List<BillingToPay> billingsToPay;
    @OneToMany(mappedBy = "user")
    private List<BillingToReceive> billingsToReceive;
}
