package com.easycommerce.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    @Size(min = 5, max = 20, message = "Username length must be between 5 and 20 characters")
    private String username;

    @Email(message = "Email is invalid")
    private String email;

    @Size(min = 8, max = 100, message = "Password length must be between 8 and 100 characters")
    private String password;

    @NotBlank(message = "Firstname is required")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
}
