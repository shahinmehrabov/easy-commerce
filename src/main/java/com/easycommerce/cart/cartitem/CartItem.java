package com.easycommerce.cart.cartitem;

import com.easycommerce.cart.Cart;
import com.easycommerce.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @PositiveOrZero
    private double discount;

    @PositiveOrZero
    private double totalPrice;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice * quantity;
        this.totalPrice = roundDouble(this.totalPrice);
    }

    private double roundDouble(double value) {
        return (double) Math.round(value * 100) / 100;
    }
}
