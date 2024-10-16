package com.easycommerce.product;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Min(value = 3, message = "Name must contain at least 3 characters")
    private String name;

    @NotBlank
    @Min(value = 5, message = "Description must contain at least 5 characters")
    private String description;
    private String imageName;

    @PositiveOrZero(message = "Quantity must be positive")
    private int quantity;

    @PositiveOrZero(message = "Price must be positive")
    private double price;

    @PositiveOrZero(message = "Discount must be positive")
    private double discount;

    @PositiveOrZero(message = "Special price must be positive")
    private double specialPrice;

    @NotNull(message = "Category is required")
    private Long categoryId;
}
