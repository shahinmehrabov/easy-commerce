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

    @Min(value = 1, message = "{cartItem.productQuantity.min.message}")
    private int productQuantity;

    @DecimalMin(value = "0.0", message = "{commons.discount.min.message}")
    @DecimalMax(value = "100.0", message = "{commons.discount.max.message}")
    private double discount;

    @PositiveOrZero(message = "{cartItem.totalPrice.error.message}")
    private double totalPrice;

    @ManyToOne
    @NotNull(message = "{cartItem.cart.NotNull.message}")
    private Cart cart;

    @ManyToOne
    @NotNull(message = "{cartItem.product.NotNull.message}")
    private Product product;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = NumberUtil.roundNumber(totalPrice);
    }
}
