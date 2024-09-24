package com.easycommerce.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min = 5, message = "Street name must have at least 5 characters")
    private String street;

    @NotBlank
    private String building;

    @NotBlank
    @Size(min = 3, message = "City name must have at least 3 characters")
    private String city;

    @NotBlank
    @Size(min = 2, message = "State name must have at least 2 characters")
    private String state;

    @NotBlank
    @Size(min = 2, message = "Country name must have at least 2 characters")
    private String country;

    @NotBlank
    @Size(min = 3, message = "Zip must have at least 3 characters")
    private String zip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
