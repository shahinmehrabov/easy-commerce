package com.easycommerce.cart.cartitem;

import com.easycommerce.cart.Cart;
import com.easycommerce.product.Product;
import com.easycommerce.util.NumberUtil;
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

    @PositiveOrZero
    private int quantity;

    @PositiveOrZero
    private double discount;

    @PositiveOrZero
    private double totalAmount;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Product product;

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = NumberUtil.roundPrice(totalAmount);
    }
}
