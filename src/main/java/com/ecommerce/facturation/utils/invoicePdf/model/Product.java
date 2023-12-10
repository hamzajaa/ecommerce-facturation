package com.ecommerce.facturation.utils.invoicePdf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Optional<String> pName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal amount;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(pName, product.pName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pName);
    }

    @Override
    public String toString() {
        return "{" +
                "pName=" + pName +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

