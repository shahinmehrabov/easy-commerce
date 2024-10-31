package com.easycommerce.cart;

import com.easycommerce.cart.cartitem.CartItem;
import com.easycommerce.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero
    private double totalPrice;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = roundDouble(totalPrice);
    }

    private double roundDouble(double value) {
        return (double) Math.round(value * 100) / 100;
    }
}
