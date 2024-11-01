package com.easycommerce.order.orderitem;

import com.easycommerce.product.ProductDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDTO {

    private Long id;

    @PositiveOrZero
    private int quantity;

    @PositiveOrZero
    private double discount;

    @PositiveOrZero
    private double orderPrice;

    @NotNull(message = "Product is required")
    private ProductDTO product;
}
