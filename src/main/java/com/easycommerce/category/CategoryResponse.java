package com.easycommerce.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> content;
    private int pageNo;
    private int pageSize;
    private String sortBy;
    private String sortOrder;
    private long totalElements;
    private int totalPages;
    private boolean isLastPage;
}
