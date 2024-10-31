package com.easycommerce.cart;

import com.easycommerce.product.ProductDTO;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDTO {

    private Long id;

    @PositiveOrZero(message = "Total price must be positive")
    private double totalPrice;
    private List<ProductDTO> products;
}
