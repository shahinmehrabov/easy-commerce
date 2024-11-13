package com.easycommerce.category;

import com.easycommerce.response.DataResponse;

public interface CategoryService {

    DataResponse<CategoryDTO> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);
    CategoryDTO getCategoryById(Long id);
    CategoryDTO getCategoryByName(String name);
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategoryById(Long id, CategoryDTO categoryDTO);
    void deleteCategoryById(Long id);
}