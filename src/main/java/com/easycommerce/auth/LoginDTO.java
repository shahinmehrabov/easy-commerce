package com.easycommerce.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username length must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password length must be between 8 and 100 characters")
    private String password;
}
