package com.easycommerce.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "{category.name.NotBlank.message}")
    private String name;
}