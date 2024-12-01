package com.easycommerce.order.orderitem;

import com.easycommerce.product.ProductDTO;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDTO {

    private Long id;

    @Min(value = 1, message = "Product quantity must be equal or greater than 1")
    private int quantity;

    @DecimalMin(value = "0.0", message = "Discount can not be less than 0.0")
    @DecimalMax(value = "100.0", message = "Discount can not be greater than 100.0")
    private double discount;

    @PositiveOrZero(message = "Order price must be equal or greater than 0")
    private double orderPrice;

    @NotNull(message = "Product is required")
    private ProductDTO product;
}
