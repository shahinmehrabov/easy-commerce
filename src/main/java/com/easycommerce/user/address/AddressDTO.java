package com.easycommerce.user.address;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class AddressDTO {

    private Long id;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Building number is required")
    private String buildingNumber;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Postal code is required")
    private String postalCode;
}
