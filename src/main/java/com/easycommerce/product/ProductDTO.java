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

    @PositiveOrZero(message = "{product.quantity.error.message}")
    private int quantity;

    @PositiveOrZero(message = "{product.price.error.message}")
    private double price;

    @DecimalMin(value = "0.0", message = "{commons.discount.min.message}")
    @DecimalMax(value = "100.0", message = "{commons.discount.max.message}")
    private double discount;

    @PositiveOrZero(message = "{product.priceAfterDiscount.error.message}")
    private double priceAfterDiscount;

    @NotNull(message = "{product.category.NotNull.message}")
    private Long categoryId;
}
