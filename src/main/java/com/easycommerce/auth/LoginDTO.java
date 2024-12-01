package com.easycommerce.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "{login.username.NotBlank.message}")
    private String username;

    @NotBlank(message = "{login.password.NotBlank.message}")
    private String password;
}
