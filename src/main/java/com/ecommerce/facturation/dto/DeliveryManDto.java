package com.ecommerce.facturation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeliveryManDto(
        @JsonProperty("fullname")
        String fullName,
        String email,
        @JsonProperty("phone")
        String phoneNumber,
        String rib
) {
}
