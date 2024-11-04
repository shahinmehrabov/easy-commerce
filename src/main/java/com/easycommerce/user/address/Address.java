package com.easycommerce.user.address;

import com.easycommerce.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3)
    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String buildingNumber;

    @Size(min = 3)
    @Column(nullable = false)
    private String city;

    @Size(min = 2)
    @Column(nullable = false)
    private String country;

    @Size(min = 3)
    @Column(nullable = false)
    private String postalCode;

    @ManyToOne
    private User user;
}
