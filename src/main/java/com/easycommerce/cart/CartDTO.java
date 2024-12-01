package com.easycommerce.cart;

import com.easycommerce.product.ProductDTO;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDTO {

    private Long id;

    @PositiveOrZero(message = "Total price must be equal or greater than 0")
    private double totalPrice;

    private List<ProductDTO> products;
}
