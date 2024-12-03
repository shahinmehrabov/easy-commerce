package com.easycommerce.order.orderitem;

import com.easycommerce.product.ProductDTO;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

    private Long id;

    @NotNull(message = "{orderItem.quantity.NotNull.message}")
    @Min(value = 1, message = "{orderItem.quantity.min.message}")
    private Integer quantity;

    @NotNull(message = "{commons.discount.NotNull.message}")
    @DecimalMin(value = "0.0", message = "{commons.discount.min.message}")
    @DecimalMax(value = "100.0", message = "{commons.discount.max.message}")
    private Double discount;

    @NotNull(message = "{orderItem.totalPrice.NotNull.message}")
    @PositiveOrZero(message = "{orderItem.totalPrice.error.message}")
    private Double totalPrice;

    @NotNull(message = "{orderItem.product.NotNull.message}")
    private ProductDTO product;
}
