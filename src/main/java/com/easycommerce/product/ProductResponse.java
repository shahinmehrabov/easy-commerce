package com.easycommerce.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private List<ProductDTO> products;
    private int pageNumber;
    private int pageSize;
    private String sortBy;
    private String sortOrder;
    private long totalElements;
    private int totalPages;
    private boolean isLastPage;
}
