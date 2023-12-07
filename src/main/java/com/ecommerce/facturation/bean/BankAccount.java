package com.ecommerce.facturation.bean;

import com.ecommerce.facturation.Enum.Bank;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends AbstractSupperClass {
    private String rib;
    @Enumerated(EnumType.STRING)
    private Bank bank;
    @ManyToOne
    private User user;
}
