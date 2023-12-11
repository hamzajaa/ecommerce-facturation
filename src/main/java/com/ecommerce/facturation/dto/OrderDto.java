package com.ecommerce.facturation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        @NotNull(message = "Reference cannot be null") @NotBlank(message = "Reference can not be blank")
        String reference,
        @NotNull(message = "Total cannot be null")
        @Min(value = 0, message = "Total must be greater than or equal to 0")
        @JsonProperty("totalPaye")
        BigDecimal totalPay,

        @NotNull(message = "Date cannot be null") @NotBlank(message = "Reference can not be blank")
        @Future(message = "Date cannot be in the future")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonProperty("dateCommande")
        LocalDateTime dateCommand,

        @NotNull(message = "Client cannot be null")
        String client,

        @NotNull(message = "Order Items Cannot be null")
        @JsonProperty("commandItems")
        String commandItemDtos

) {
}
