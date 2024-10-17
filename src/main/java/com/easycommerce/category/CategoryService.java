package com.easycommerce.category;

public interface CategoryService {

    CategoryResponse getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);
    CategoryDTO getCategoryById(Long id);
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategoryById(Long id, CategoryDTO categoryDTO);
    void deleteCategoryById(Long id);
}