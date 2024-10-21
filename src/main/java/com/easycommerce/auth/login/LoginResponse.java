package com.easycommerce.auth.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String jwtToken;
    private long expiresInMs;
}
