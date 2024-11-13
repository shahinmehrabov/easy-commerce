package com.easycommerce.category;

import com.easycommerce.exception.ResourceAlreadyExistsException;
import com.easycommerce.exception.ResourceNotFoundException;
import com.easycommerce.response.DataResponse;
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
    public DataResponse<CategoryDTO> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Pageable pageable = buildPageable(sortBy, sortOrder, pageNumber, pageSize);
        Page<Category> page = categoryRepository.findAll(pageable);
        return buildDataResponse(page, sortBy, sortOrder);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = findCategoryById(id);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category = findCategoryByName(name);
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

        Category category = findCategoryById(id);
        category.setName(categoryDTO.getName());

        Category savedCategory = categoryRepository.save(category);
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

    private Category findCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", name));
    }

    private Pageable buildPageable(String sortBy, String sortOrder, int pageNumber, int pageSize) {
        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private DataResponse<CategoryDTO> buildDataResponse(Page<Category> page, String sortBy, String sortOrder) {
        List<CategoryDTO> categories = page.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        return DataResponse.<CategoryDTO>builder()
                .data(categories)
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