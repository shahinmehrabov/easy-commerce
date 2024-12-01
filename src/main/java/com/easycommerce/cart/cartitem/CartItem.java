package com.easycommerce.cart.cartitem;

import com.easycommerce.cart.Cart;
import com.easycommerce.product.Product;
import com.easycommerce.util.NumberUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Product quantity must be equal or greater than 1")
    private int productQuantity;

    @DecimalMin(value = "0.0", message = "Discount can not be less than 0.0")
    @DecimalMax(value = "100.0", message = "Discount can not be greater than 100.0")
    private double discount;

    @PositiveOrZero(message = "Total price must be equal or greater than 0")
    private double totalPrice;

    @ManyToOne
    @NotNull(message = "Cart is required")
    private Cart cart;

    @ManyToOne
    @NotNull(message = "Product is required")
    private Product product;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = NumberUtil.roundNumber(totalPrice);
    }
}
