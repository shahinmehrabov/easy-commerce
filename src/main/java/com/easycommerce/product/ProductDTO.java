package com.easycommerce.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private long id;

    @NotBlank
    @Size(min = 3, message = "Product name must contain at least 3 characters")
    private String name;

    @NotBlank
    @Size(min = 5, message = "Product description must contain at least 5 characters")
    private String description;
    private String image;
    private int quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @NotNull(message = "Category can not be null")
    private Long categoryID;
}
