package com.easycommerce.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 4, max = 20, message = "Username must have min 4 and max 20 characters")
    private String username;

    @NotBlank
    @Email
    @Size(max = 50, message = "Email can not have more than 50 characters")
    private String email;

    @NotBlank
    @Size(min = 8, max = 100, message = "Password must have min 8 and max 100 characters")
    private String password;

    private Set<String> roles;
}
