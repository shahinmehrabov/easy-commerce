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
    private double totalAmount;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    public void setTotalAmount() {
        totalAmount = product.getPriceAfterDiscount() * quantity;
        totalAmount = roundDouble(totalAmount);
    }

    private double roundDouble(double value) {
        return (double) Math.round(value * 100) / 100;
    }
}