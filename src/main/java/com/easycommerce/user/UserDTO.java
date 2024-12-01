package com.easycommerce.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;

    @Size(min = 5, max = 20, message = "{user.username.size.message}")
    private String username;

    @Email(message = "{user.email.error.message}")
    private String email;

    @NotBlank(message = "{user.firstName.NotBlank.message}")
    private String firstName;

    @NotBlank(message = "{user.lastName.NotBlank.message}")
    private String lastName;

    @NotBlank(message = "{user.phoneNumber.NotBlank.message}")
    private String phoneNumber;
}
