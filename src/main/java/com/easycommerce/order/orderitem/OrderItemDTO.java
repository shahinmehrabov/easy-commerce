package com.easycommerce.order.orderitem;

import com.easycommerce.product.ProductDTO;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

    private Long id;

    @Min(value = 1, message = "{orderItem.quantity.min.message}")
    private int quantity;

    @DecimalMin(value = "0.0", message = "{commons.discount.min.message}")
    @DecimalMax(value = "100.0", message = "{commons.discount.max.message}")
    private double discount;

    @PositiveOrZero(message = "{orderItem.orderPrice.error.message}")
    private double orderPrice;

    @NotNull(message = "{orderItem.product.NotNull.message}")
    private ProductDTO product;
}
