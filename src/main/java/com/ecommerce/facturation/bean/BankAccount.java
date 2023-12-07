package com.ecommerce.facturation.bean;

import jakarta.persistence.Entity;
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
    private String bank;
    @ManyToOne
    private User user;
}
