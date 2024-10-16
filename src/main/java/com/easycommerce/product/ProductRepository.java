package com.easycommerce.product;

import com.easycommerce.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategory(Category category, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(concat('%', :keyword, '%'))")
    Page<Product> findByKeyword(String keyword, Pageable pageable);
}
