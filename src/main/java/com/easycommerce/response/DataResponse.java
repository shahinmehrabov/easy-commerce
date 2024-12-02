package com.easycommerce.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse<T> {

    private List<T> data;
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String sortOrder;
    private Long totalElements;
    private Integer totalPages;
    private boolean isLastPage;
}
