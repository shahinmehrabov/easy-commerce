package com.easycommerce.category;

import com.easycommerce.exception.APIException;
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
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = buildSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return buildCategoryResponse(categoryPage, sortBy, sortOrder);
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = getCategoryOrThrowById(categoryId);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        throwIfCategoryExistsByName(categoryDTO.getName());

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        getCategoryOrThrowById(categoryId);
        throwIfCategoryExistsByName(categoryDTO.getName());

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setId(categoryId);

        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = getCategoryOrThrowById(categoryId);
        categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
    }

    private void throwIfCategoryExistsByName(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name))
            throw new APIException("Category", "name", name);
    }

    private Category getCategoryOrThrowById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
    }

    private Sort buildSort(String sortBy, String sortOrder) {
        return sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
    }

    private CategoryResponse buildCategoryResponse(Page<Category> categoryPage, String sortBy, String sortOrder) {
        List<CategoryDTO> categories = categoryPage.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        return CategoryResponse
                .builder()
                .categories(categories)
                .pageNumber(categoryPage.getNumber())
                .pageSize(categoryPage.getSize())
                .sortBy(sortBy)
                .sortOrder(sortOrder.equalsIgnoreCase("desc") ? "desc" : "asc")
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .isLastPage(categoryPage.isLast())
                .build();
    }
}
