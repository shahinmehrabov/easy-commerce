package com.easycommerce.order;

import com.easycommerce.cart.Cart;
import com.easycommerce.cart.CartRepository;
import com.easycommerce.cart.cartitem.CartItem;
import com.easycommerce.exception.APIException;
import com.easycommerce.exception.ResourceNotFoundException;
import com.easycommerce.order.orderitem.OrderItem;
import com.easycommerce.order.orderitem.OrderItemDTO;
import com.easycommerce.order.orderitem.OrderItemRepository;
import com.easycommerce.product.Product;
import com.easycommerce.product.ProductRepository;
import com.easycommerce.user.User;
import com.easycommerce.user.UserService;
import com.easycommerce.user.address.Address;
import com.easycommerce.user.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final UserService userService;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDTO placeOrder(Long addressId) {
        User user = userService.getLoggedInUser();
        Cart cart = user.getCart();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        Order order = Order
                .builder()
                .totalAmount(cart.getTotalPrice())
                .orderDate(LocalDateTime.now())
                .user(user)
                .address(address)
                .build();
        Order savedOrder = orderRepository.save(order);
        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems == null || cartItems.isEmpty())
            throw new APIException("Cart is empty");

        List<OrderItem> orderItems = cartItems
                .stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductQuantity(item.getQuantity());
                    orderItem.setDiscount(item.getDiscount());
                    orderItem.setProduct(item.getProduct());
                    orderItem.setOrder(savedOrder);
                    orderItem.setTotalPrice(item.getTotalAmount());
                    return orderItem;
                })
                .toList();

        orderItems = orderItemRepository.saveAll(orderItems);

        cart.getCartItems().forEach(item -> {
            int quantity = item.getQuantity();
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
        });

        cart.setTotalPrice(0.0);
        cart.getCartItems().clear();
        cartRepository.save(cart);

        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
        orderDTO.setOrderItems(orderItems
                .stream()
                .map(item -> modelMapper.map(item, OrderItemDTO.class))
                .toList());

        return orderDTO;
    }
}
