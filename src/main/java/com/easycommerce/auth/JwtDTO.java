package com.easycommerce.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtDTO {

    @NotBlank(message = "Token is required")
    private String token;

    @NotBlank(message = "Expiration in milliseconds is required")
    private long expiresInMs;
}
