package com.easycommerce.product;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Long id;

    @NotBlank(message = "{product.name.NotBlank.message}")
    private String name;

    @NotBlank(message = "{product.description.NotBlank.message}")
    private String description;
    private String imageName;

    @NotNull(message = "{product.quantity.NotNull.message}")
    @PositiveOrZero(message = "{product.quantity.error.message}")
    private Integer quantity;

    @NotNull(message = "{product.price.NotNull.message}")
    @PositiveOrZero(message = "{product.price.error.message}")
    private Double price;

    @NotNull(message = "{commons.discount.NotNull.message}")
    @DecimalMin(value = "0.0", message = "{commons.discount.min.message}")
    @DecimalMax(value = "100.0", message = "{commons.discount.max.message}")
    private Double discount;

    @PositiveOrZero(message = "{product.priceAfterDiscount.error.message}")
    private Double priceAfterDiscount;

    @NotNull(message = "{product.category.NotNull.message}")
    private Long categoryId;
}
