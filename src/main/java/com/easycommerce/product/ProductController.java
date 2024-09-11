package com.easycommerce.product;

import com.easycommerce.config.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {

        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/search/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @PathVariable String keyword,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {

        ProductResponse productResponse = productService.getProductsByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryID}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable long categoryID,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {

        ProductResponse productResponse = productService.getProductsByCategory(categoryID, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/{productID}")
    public ResponseEntity<ProductDTO> getProductByID(@PathVariable long productID) {
        ProductDTO productDTO = productService.getProductByID(productID);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/admin/products")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO savedProductDTO = productService.addProduct(productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin/products/{productID}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable long productID) {
        ProductDTO updatedProductDTO = productService.updateProduct(productDTO, productID);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productID}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable long productID,
                                                         @RequestParam("image") MultipartFile image) throws IOException {

        ProductDTO productDTO = productService.updateProductImage(productID, image);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productID}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable long productID) {
        ProductDTO productDTO = productService.deleteProduct(productID);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
