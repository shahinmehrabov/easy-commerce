package com.easycommerce.category;

import com.easycommerce.exception.ResourceAlreadyExistsException;
import com.easycommerce.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = buildSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);

        return getCategoryResponse(page, sortBy, sortOrder);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = findCategoryById(id);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        existsByName(categoryDTO.getName());

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategoryById(Long id, CategoryDTO categoryDTO) {
        existsByName(categoryDTO.getName());

        Category currentCategory = findCategoryById(id);
        currentCategory.setName(categoryDTO.getName());

        Category savedCategory = categoryRepository.save(currentCategory);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    private void existsByName(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name))
            throw new ResourceAlreadyExistsException("Category", "name", name);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    private Sort buildSort(String sortBy, String sortOrder) {
        return sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
    }

    private CategoryResponse getCategoryResponse(Page<Category> page, String sortBy, String sortOrder) {
        List<CategoryDTO> categories = page.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        return CategoryResponse.builder()
                .categories(categories)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .sortBy(sortBy)
                .sortOrder(sortOrder.equalsIgnoreCase("desc") ? "desc" : "asc")
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLastPage(page.isLast())
                .build();
    }
}