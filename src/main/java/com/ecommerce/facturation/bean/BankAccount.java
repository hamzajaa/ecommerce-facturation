package com.ecommerce.facturation.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccount extends AbstractSupperClass{
    private String rib;
    private String Bank;
    @ManyToOne
    private User user;
}
