package com.ecommerce.facturation.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccountBalance extends AbstractSupperClass {
    @OneToOne
    private BankAccount bankAccount;
    private BigDecimal balance;
}
// this entity will have just the balance of our company (probably a table with one row)
// may have other line if the company got more than one bank account