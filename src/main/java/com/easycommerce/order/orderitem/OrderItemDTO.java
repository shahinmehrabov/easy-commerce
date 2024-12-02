package com.easycommerce.order.orderitem;

import com.easycommerce.product.ProductDTO;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

    private Long id;

    @NotNull(message = "{orderItem.productQuantity.NotNull.message}")
    @Min(value = 1, message = "{orderItem.productQuantity.min.message}")
    private Integer productQuantity;

    @NotNull(message = "{commons.discount.NotNull.message}")
    @DecimalMin(value = "0.0", message = "{commons.discount.min.message}")
    @DecimalMax(value = "100.0", message = "{commons.discount.max.message}")
    private Double discount;

    @NotNull(message = "{orderItem.orderPrice.NotNull.message}")
    @PositiveOrZero(message = "{orderItem.orderPrice.error.message}")
    private Double orderPrice;

    @NotNull(message = "{orderItem.product.NotNull.message}")
    private ProductDTO product;
}
