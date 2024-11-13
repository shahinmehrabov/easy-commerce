package com.easycommerce.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, message = "Name must contain at least 3 characters")
    private String name;
}