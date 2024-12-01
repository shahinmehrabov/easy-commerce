package com.easycommerce.product;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;
    private String imageName;

    @PositiveOrZero(message = "Quantity must be equal or greater than 0")
    private int quantity;

    @PositiveOrZero(message = "Price must be equal or greater than 0")
    private double price;

    @DecimalMin(value = "0.0", message = "Discount can not be less than 0.0")
    @DecimalMax(value = "100.0", message = "Discount can not be greater than 100.0")
    private double discount;

    @PositiveOrZero(message = "Price after discount must be equal or greater than 0")
    private double priceAfterDiscount;

    @NotNull(message = "Category is required")
    private Long categoryId;
}
