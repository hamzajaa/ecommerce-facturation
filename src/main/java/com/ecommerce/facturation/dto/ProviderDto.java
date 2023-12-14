package com.ecommerce.facturation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProviderDto(
        Long id,
        @JsonProperty("name")
        String fullName,
        @JsonProperty("mail")
        String email,
        String rib
) {
}
