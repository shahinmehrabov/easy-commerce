package com.easycommerce.auth.login;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponse {

    private String jwtToken;
    private Long expiresIn;
}
