package com.easycommerce.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;

    @NotBlank
    @Size(min = 3, message = "Category name must contain at least 3 characters")
    private String name;
}
