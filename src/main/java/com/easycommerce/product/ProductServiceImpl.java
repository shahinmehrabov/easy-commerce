package com.easycommerce.product;

import com.easycommerce.category.Category;
import com.easycommerce.category.CategoryRepository;
import com.easycommerce.exception.ResourceNotFoundException;
import com.easycommerce.image.ImageService;
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

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    @Value("${product.image.path}")
    private String imagePath;

    @Value("${product.default.image.name}")
    private String defaultImageName;

    @Override
    public ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findAll(pageable);

        return getProductResponse(productPage, sortBy, sortOrder);
    }

    @Override
    public ProductResponse getProductsByCategory(long categoryID, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Category category = getCategoryByID(categoryID);
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findByCategory(category, pageable);

        return getProductResponse(productPage, sortBy, sortOrder);
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findByNameLikeIgnoreCase("%" + keyword + "%", pageable);

        return getProductResponse(productPage, sortBy, sortOrder);
    }

    @Override
    public ProductDTO getProductByID(long productID) {
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productID));

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Category category = getCategoryByID(productDTO.getCategoryID());
        Product product = modelMapper.map(productDTO, Product.class);

        product.setSpecialPrice();
        product.setImage(defaultImageName);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, long productID) {
        Product currentProduct = modelMapper.map(getProductByID(productID), Product.class);
        Category category = getCategoryByID(productDTO.getCategoryID());

        currentProduct.setName(productDTO.getName());
        currentProduct.setDescription(productDTO.getDescription());
        currentProduct.setQuantity(productDTO.getQuantity());
        currentProduct.setPrice(productDTO.getPrice());
        currentProduct.setDiscount(productDTO.getDiscount());
        currentProduct.setSpecialPrice();
        currentProduct.setCategory(category);

        Product savedProduct = productRepository.save(currentProduct);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(long productID) {
        Product product = modelMapper.map(getProductByID(productID), Product.class);

        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(long productID, MultipartFile image) throws IOException {
        Product product = modelMapper.map(getProductByID(productID), Product.class);
        String imageName = imageService.uploadImage(imagePath, image);
        product.setImage(imageName);

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    private Category getCategoryByID(long categoryID) {
        return categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryID));
    }

    private Sort getSort(String sortBy, String sortOrder) {
        return sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    }

    private ProductResponse getProductResponse(Page<Product> productPage, String sortBy, String sortOrder) {
        List<ProductDTO> productDTOs = productPage.stream().map(
                product -> modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOs);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setSortBy(sortBy);
        productResponse.setSortOrder(sortOrder);
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }
}
