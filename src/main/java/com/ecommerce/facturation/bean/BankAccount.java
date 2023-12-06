package com.ecommerce.facturation.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class BankAccount extends AbstractSupperClass{
    private String rib;
    private String Bank;
    @ManyToOne
    private User user;
}
