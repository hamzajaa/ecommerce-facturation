package com.ecommerce.facturation.dto;
import com.ecommerce.facturation.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record UserDTO(
    Long id,

    @NotBlank(message = "Full name cannot be blank")
    String fullName,

    @Email(message = "Email should be valid")
    String email,

    @NotBlank(message = "Address cannot be blank")
    String address,

    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    String phoneNumber,

    @NotNull(message = "Role cannot be null")
    Role role
){

}
