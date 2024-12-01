package com.easycommerce.auth;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    @Size(min = 5, max = 20, message = "Username length must be between 5 and 20 characters")
    private String username;

    @Size(min = 8, max = 100, message = "Password length must be between 8 and 100 characters")
    private String password;
}
