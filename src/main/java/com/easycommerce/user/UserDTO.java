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

    @Size(min = 5, max = 20, message = "Username length must be between 5 and 20 characters")
    private String username;

    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Firstname is required")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
}
