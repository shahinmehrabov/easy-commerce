package com.easycommerce.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart() {
        CartDTO cartDTO = cartService.getCart();
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PostMapping("/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable int quantity) {

        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @PutMapping("/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> updateProductQuantityInCart(@PathVariable Long productId,
                                                              @PathVariable int quantity) {

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<CartDTO> deleteProductFromCartById(@PathVariable Long productId) {
        cartService.deleteProductFromCart(productId);

        CartDTO cartDTO = cartService.getCart();
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<CartDTO> removeAllProductsFromCart() {
        CartDTO cartDTO = cartService.removeAllProductsFromCart();
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
}
