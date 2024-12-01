package com.easycommerce.user.address;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class AddressDTO {

    private Long id;

    @NotBlank(message = "{address.street.NotBlank.message}")
    private String street;

    @NotBlank(message = "{address.buildingNumber.NotBlank.message}")
    private String buildingNumber;

    @NotBlank(message = "{address.city.NotBlank.message}")
    private String city;

    @NotBlank(message = "{address.country.NotBlank.message}")
    private String country;

    @NotBlank(message = "{address.postalCode.NotBlank.message}")
    private String postalCode;
}
