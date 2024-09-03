package com.easycommerce.category;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(id);
        return new ResponseEntity<>(deletedCategoryDTO, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryDTO, id);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }
}
