package com.easycommerce.cart;

import com.easycommerce.cart.cartitem.CartItem;
import com.easycommerce.cart.cartitem.CartItemRepository;
import com.easycommerce.exception.APIException;
import com.easycommerce.exception.ResourceAlreadyExistsException;
import com.easycommerce.exception.ResourceNotFoundException;
import com.easycommerce.product.*;
import com.easycommerce.user.User;
import com.easycommerce.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public CartDTO getCart() {
        Cart cart = getOrCreateCart();
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<ProductDTO> products = cart.getCartItems().stream()
                .map(item -> {
                    item.getProduct().setQuantity(item.getQuantity());
                    return modelMapper.map(item.getProduct(), ProductDTO.class);
                })
                .toList();

        cartDTO.setProducts(products);
        return cartDTO;
    }

    @Override
    public CartDTO addProductToCart(Long productId, int quantity) {
        Cart cart = getOrCreateCart();
        Product product = findProductById(productId);

        if (cartItemRepository.existsByCartAndProduct(cart, product))
            throw new ResourceAlreadyExistsException(String.format("Product %s already exists in the cart", product.getName()));

        if (product.getQuantity() == 0)
            throw new APIException(String.format("%s is not available", product.getName()));

        if (product.getQuantity() < quantity)
            throw new APIException(String.format("Please add the %s less than or equal to the quantity %s",
                    product.getName(), product.getQuantity()));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(quantity);
        cartItem.setDiscount(product.getDiscount());
        cartItem.setTotalPrice(product.getPriceAfterDiscount() * quantity);
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        cart.getCartItems().add(savedCartItem);
        cart.setTotalPrice(cart.getTotalPrice() + (product.getPriceAfterDiscount() * quantity));
        cartRepository.save(cart);

        return getCart();
    }

    @Override
    public CartDTO updateProductQuantityInCart(Long productId, int quantity) {
        Cart cart = getOrCreateCart();
        Product product = findProductById(productId);

        if (product.getQuantity() == 0)
            throw new APIException(String.format("%s is not available", product.getName()));

        if (product.getQuantity() < quantity)
            throw new APIException(String.format("Please add the %s less than or equal to the quantity %s",
                    product.getName(), product.getQuantity()));

        CartItem cartItem = findCartItemByProductAndCart(product, cart);

        if (cartItem.getQuantity() + quantity <= 0) {
            if (cartItemRepository.existsByCartAndProduct(cart, product))
                deleteProductFromCart(productId);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(product.getPriceAfterDiscount() * cartItem.getQuantity());
            cartItem.setDiscount(product.getDiscount());
            cart.setTotalPrice(cart.getTotalPrice() + (quantity * product.getPriceAfterDiscount()));

            cartRepository.save(cart);
        }

        return getCart();
    }

    @Override
    public CartDTO removeAllProductsFromCart() {
        Cart cart = getOrCreateCart();
        cart.setTotalPrice(0.0);
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return getCart();
    }

    @Override
    public void deleteProductFromCart(Long productId) {
        Cart cart = getOrCreateCart();
        Product product = findProductById(productId);
        CartItem cartItem = findCartItemByProductAndCart(product, cart);

        cartItemRepository.delete(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getTotalPrice());
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart() {
        User user = userService.getLoggedInUser();
        Cart userCart = cartRepository.findByUser(user);

        if (userCart != null)
            return userCart;

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setUser(user);
        cart.setCartItems(new ArrayList<>());

        return cartRepository.save(cart);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    private CartItem findCartItemByProductAndCart(Product product, Cart cart) {
        return cartItemRepository.findByProductAndCart(product, cart)
                .orElseThrow(() -> new APIException(String.format("Product '%s' is not available in the cart", product.getName())));
    }
}
