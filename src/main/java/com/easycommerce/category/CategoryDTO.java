package com.easycommerce.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Category name must contain at least 3 characters")
    private String name;
}