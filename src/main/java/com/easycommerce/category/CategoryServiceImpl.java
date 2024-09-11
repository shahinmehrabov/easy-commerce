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

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return getCategoryResponse(categoryPage, sortBy, sortOrder);
    }

    @Override
    public CategoryResponse getCategoriesByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findByNameLikeIgnoreCase("%" + keyword + "%", pageable);

        return getCategoryResponse(categoryPage, sortBy, sortOrder);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName()))
            throw new APIException(String.format("Category with the name '%s' already exists", categoryDTO.getName()));

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, long categoryID) {
        if (!categoryRepository.existsById(categoryID))
            throw new ResourceNotFoundException("Category", "id", categoryID);

        if (categoryRepository.existsByName(categoryDTO.getName()))
            throw new APIException(String.format("Category with the name '%s' already exists", categoryDTO.getName()));

        categoryDTO.setId(categoryID);
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(long categoryID) {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryID));

        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    private Sort getSort(String sortBy, String sortOrder) {
        return sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    }

    private CategoryResponse getCategoryResponse(Page<Category> categoryPage, String sortBy, String sortOrder) {
        List<CategoryDTO> categoryDTOs = categoryPage.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class)).toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategories(categoryDTOs);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setSortBy(sortBy);
        categoryResponse.setSortOrder(sortOrder);
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;
    }
}
