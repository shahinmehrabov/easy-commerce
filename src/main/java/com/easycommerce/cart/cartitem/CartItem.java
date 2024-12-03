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

    @NotNull(message = "Product quantity can not be null")
    @Min(value = 1, message = "Product quantity must be equal or greater than 1")
    @Column(nullable = false)
    private Integer productQuantity;

    @Column(nullable = false)
    @NotNull(message = "Discount can not be null")
    @DecimalMin(value = "0.0", message = "Discount can not be less than 0.0")
    @DecimalMax(value = "100.0", message = "Discount can not be greater than 100.0")
    private Double discount;

    @Column(nullable = false)
    @NotNull(message = "Total price can not be null")
    @PositiveOrZero(message = "Total price must be equal or greater than 0")
    private Double totalPrice;

    @ManyToOne
    @NotNull(message = "Cart can not be null")
    private Cart cart;

    @ManyToOne
    @NotNull(message = "Product can not be null")
    private Product product;

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = NumberUtil.roundNumber(totalPrice);
    }
}
