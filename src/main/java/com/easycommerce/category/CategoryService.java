package com.easycommerce.category;

public interface CategoryService {
    CategoryResponse getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);
    CategoryResponse getCategoriesByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortOrder);
    CategoryDTO getCategoryByID(long categoryID);
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(long categoryID);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, long categoryID);
}
