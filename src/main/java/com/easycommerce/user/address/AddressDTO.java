package com.easycommerce.user.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;

    @NotBlank(message = "Street is required")
    @Size(min = 3, message = "Street must have at least 3 characters")
    private String street;

    @NotBlank(message = "Building is required")
    private String building;

    @NotBlank(message = "City is required")
    @Size(min = 3, message = "City name must have at least 3 characters")
    private String city;
    private String state;

    @NotBlank(message = "Country is required")
    @Size(min = 2, message = "Country must have at least 2 characters")
    private String country;

    @NotBlank(message = "Zip is required")
    @Size(min = 3, message = "Zip must have at least 3 characters")
    private String zip;
}
