package com.easycommerce.cart;

public interface CartService {

    CartDTO getCart();
    CartDTO addProductToCart(Long productId, int quantity);
    CartDTO updateProductQuantityInCart(Long productId, int quantity);
    CartDTO removeAllProductsFromCart();
    void deleteProductFromCart(Long productId);
}
