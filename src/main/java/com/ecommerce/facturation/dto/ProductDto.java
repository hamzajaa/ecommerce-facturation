package com.ecommerce.facturation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProductDto(
        @JsonProperty("Product_id")
        Long id,
        @JsonProperty("nomProduit")
        String name,
        @JsonProperty("prixProduit")
        BigDecimal unitPrice

) {
}
