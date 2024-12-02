package com.easycommerce.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtDTO {

    @NotBlank(message = "{jwt.token.NotBlank.message}")
    private String token;

    @NotNull(message = "{jwt.expiresInMs.NotNull.message}")
    private Long expiresInMs;
}
