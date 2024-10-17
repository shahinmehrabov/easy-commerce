package com.easycommerce.product;

import com.easycommerce.config.AppConstants;
import com.easycommerce.exception.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {

        ProductResponse productResponse =
                productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/categories/id/{categoryId}")
    public ResponseEntity<ProductResponse> getProductsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {

        ProductResponse productResponse =
                productService.getProductsByCategoryId(categoryId, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/categories/name/{categoryName}")
    public ResponseEntity<ProductResponse> getProductsByCategoryName(
            @PathVariable String categoryName,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {

        ProductResponse productResponse =
                productService.getProductsByCategoryName(categoryName, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @PathVariable String keyword,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {

        ProductResponse productResponse = productService
                .getProductsByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO savedProductDTO = productService.addProduct(productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productService.updateProductById(id, productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<ProductDTO> updateProductImageById(@PathVariable Long id,
                                                             @RequestParam("image") MultipartFile image) throws IOException {

        ProductDTO updatedProductDTO = productService.updateProductImageById(id, image);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);

        APIResponse response = new APIResponse("Successfully deleted product with id: " + id, new Date());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
