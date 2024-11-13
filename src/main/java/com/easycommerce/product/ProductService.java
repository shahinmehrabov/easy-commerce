package com.easycommerce.product;

import com.easycommerce.response.DataResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    DataResponse<ProductDTO> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);
    DataResponse<ProductDTO> getProductsByCategoryId(Long categoryId, int pageNumber, int pageSize, String sortBy, String sortOrder);
    DataResponse<ProductDTO> getProductsByCategoryName(String categoryName, int pageNumber, int pageSize, String sortBy, String sortOrder);
    DataResponse<ProductDTO> getProductsByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortOrder);
    ProductDTO getProductById(Long id);
    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO updateProductById(Long id, ProductDTO productDTO);
    void deleteProductById(Long id);
    ProductDTO updateProductImageById(Long id, MultipartFile image) throws IOException;
}