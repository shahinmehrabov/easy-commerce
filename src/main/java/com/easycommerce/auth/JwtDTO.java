package com.easycommerce.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtDTO {

    @NotBlank(message = "{jwt.token.NotBlank.message}")
    private String token;

    @NotBlank(message = "{jwt.expiresInMs.NotBlank.message}")
    private long expiresInMs;
}
