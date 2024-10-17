package com.easycommerce.product;

import com.easycommerce.auth.util.AuthUtil;
import com.easycommerce.category.Category;
import com.easycommerce.category.CategoryRepository;
import com.easycommerce.exception.ResourceNotFoundException;
import com.easycommerce.image.ImageService;
import com.easycommerce.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;
    private final AuthUtil authUtil;

    @Value("${product.image.path}")
    private String imagePath;

    @Value("${product.image.default-name}")
    private String defaultImageName;

    @Override
    public ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);

        return getProductResponse(page, sortBy, sortOrder);
    }

    @Override
    public ProductResponse getProductsByCategoryId(Long categoryId, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Category category = findCategoryById(categoryId);
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByCategory(category, pageable);

        return getProductResponse(page, sortBy, sortOrder);
    }

    @Override
    public ProductResponse getProductsByCategoryName(String name, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Category category = findCategoryByName(name);
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByCategory(category, pageable);

        return getProductResponse(page, sortBy, sortOrder);
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByKeyword(keyword, pageable);

        return getProductResponse(page, sortBy, sortOrder);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = findProductById(id);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Category category = findCategoryById(productDTO.getCategoryId());
        Product product = modelMapper.map(productDTO, Product.class);
        User user = authUtil.loggedInUser();

        product.setUser(user);
        product.setPriceAfterDiscount();
        product.setImageName(defaultImageName);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductById(Long id, ProductDTO productDTO) {
        Product currentProduct = findProductById(id);
        Product updatedProduct = modelMapper.map(productDTO, Product.class);

        currentProduct.setName(updatedProduct.getName());
        currentProduct.setDescription(updatedProduct.getDescription());
        currentProduct.setQuantity(updatedProduct.getQuantity());
        currentProduct.setDiscount(updatedProduct.getDiscount());
        currentProduct.setPrice(updatedProduct.getPrice());
        currentProduct.setPriceAfterDiscount();

        Product savedProduct = productRepository.save(currentProduct);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public void deleteProductById(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    @Override
    public ProductDTO updateProductImageById(Long id, MultipartFile image) throws IOException {
        Product product = findProductById(id);
        String imageName = imageService.uploadImage(imagePath, image);

        product.setImageName(imageName);

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    private Category findCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", name));
    }

    private Sort getSort(String sortBy, String sortOrder) {
        return sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
    }

    private ProductResponse getProductResponse(Page<Product> page, String sortBy, String sortOrder) {
        List<ProductDTO> products = page.stream().map(
                product -> modelMapper.map(product, ProductDTO.class)).toList();

        return ProductResponse.builder()
                .products(products)
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
