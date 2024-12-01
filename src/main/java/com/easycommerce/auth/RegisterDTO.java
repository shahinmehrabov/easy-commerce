package com.easycommerce.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    @NotBlank(message = "{register.username.NotBlank.message}")
    @Size(min = 5, max = 20, message = "{register.username.size.message}")
    private String username;

    @Email(message = "{register.email.error.message}")
    private String email;

    @NotBlank(message = "{register.password.NotBlank.message}")
    @Size(min = 8, max = 100, message = "{register.password.size.message}")
    private String password;

    @NotBlank(message = "{register.firstName.NotBlank.message}")
    private String firstName;

    @NotBlank(message = "{register.lastName.NotBlank.message}")
    private String lastName;

    @NotBlank(message = "{register.phoneNumber.NotBlank.message}")
    private String phoneNumber;
}
