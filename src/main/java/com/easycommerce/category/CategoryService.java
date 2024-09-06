package com.easycommerce.category;

public interface CategoryService {
    CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long id);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id);
}
