package com.easycommerce.cart.cartitem;

import com.easycommerce.cart.CartDTO;
import com.easycommerce.product.ProductDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDTO {

    private Long id;
    private int quantity;

    @PositiveOrZero(message = "Discount must be positive")
    private double discount;

    @PositiveOrZero(message = "Price must be positive")
    private double price;

    @NotNull(message = "Cart is required")
    private CartDTO cartDTO;

    @NotNull(message = "Product is required")
    private ProductDTO productDTO;
}
