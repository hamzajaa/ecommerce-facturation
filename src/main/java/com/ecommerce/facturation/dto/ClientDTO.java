package com.ecommerce.facturation.dto;

import jakarta.validation.constraints.*;

public record ClientDTO(
        @NotNull(message = "Full name cannot be null") @NotBlank(message = "Full name cannot be blank")
        String fullName,
        @NotNull(message = "Email cannot be null")  @NotBlank(message = "Email cannot be blank")
        @Email(message = "Not valid Email")
        String email,
        @NotBlank(message = "Address cannot be blank")
        String address,
        @NotNull(message = "Phone number cannot be null") @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp = "^\\+?[0-9. ()-]*$", message = "Invalid phone number format")
        @Size(min = 10, max = 14, message = "Phone number length should be between 10 and 15 characters")
        String phoneNumber
) {

}
