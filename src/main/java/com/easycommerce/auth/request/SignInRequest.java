package com.easycommerce.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {

    @NotBlank
    @Size(min = 4, max = 20, message = "Username must have min 4 and max 20 characters")
    private String username;

    @NotBlank
    @Size(min = 8, max = 100, message = "Password must have min 8 and max 100 characters")
    private String password;
}
