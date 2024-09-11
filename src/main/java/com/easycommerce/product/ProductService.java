package com.easycommerce.product;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);
    ProductResponse getProductsByCategory(long categoryID, int pageNumber, int pageSize, String sortBy, String sortOrder);
    ProductResponse getProductsByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortOrder);
    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO, long productID);
    ProductDTO deleteProduct(long productID);
    ProductDTO updateProductImage(long productID, MultipartFile image) throws IOException;
}
