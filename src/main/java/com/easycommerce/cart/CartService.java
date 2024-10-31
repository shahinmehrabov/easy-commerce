package com.easycommerce.cart;

public interface CartService {

    CartDTO getCart();
    CartDTO addProductToCart(Long productId, int quantity);
    CartDTO updateProductQuantityInCart(Long productId, int quantity);
    void deleteProductFromCart(Long productId);
}
