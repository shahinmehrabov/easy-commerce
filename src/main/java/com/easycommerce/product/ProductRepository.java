package com.easycommerce.product;

import com.easycommerce.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String productName);
    Page<Product> findByCategory(Category category, Pageable pageable);
    Page<Product> findByNameLikeIgnoreCase(String keyword, Pageable pageable);
}
