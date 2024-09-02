package com.easycommerce.category;

import com.easycommerce.exception.APIException;
import com.easycommerce.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty())
            throw new APIException("No category found");

        return categories;
    }

    @Override
    public void createCategory(Category category) {
        verifyCategory(category.getName());
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long id) {
        verifyCategory(id);
        categoryRepository.deleteById(id);

        return "Category with id = " + id + " deleted";
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        verifyCategory(id, category.getName());
        category.setId(id);

        return categoryRepository.save(category);
    }

    private void verifyCategory(Long id, String name) {
        verifyCategory(id);
        verifyCategory(name);
    }

    private void verifyCategory(Long id) {
        if (!categoryRepository.existsById(id))
            throw new ResourceNotFoundException("Category", "id", id);
    }

    private void verifyCategory(String name) {
        if (categoryRepository.existsByName(name))
            throw new APIException(String.format("Category with the name %s already exists", name));
    }
}
