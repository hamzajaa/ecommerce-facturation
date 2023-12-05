package com.ecommerce.facturation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CommandItemDto(
        Long id,
        @NotNull(message = "Quantity cannot be null")
        @Min(value = 0, message = "Price must be greater than or equal to 0")
        int quantity,

        @NotNull(message = "Price cannot be null")
        @Min(value = 0, message = "Price must be greater than or equal to 0")
        @JsonProperty("prix")
        BigDecimal amount,
        @NotNull(message = "Product cannot be null")
        @JsonProperty("produit")
        ProductDto product
) {
}
