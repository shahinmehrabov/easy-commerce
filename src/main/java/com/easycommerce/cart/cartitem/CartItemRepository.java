package com.easycommerce.cart.cartitem;

import com.easycommerce.cart.Cart;
import com.easycommerce.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProductAndCart(Product product, Cart cart);
    boolean existsByCartAndProduct(Cart cart, Product product);
}
