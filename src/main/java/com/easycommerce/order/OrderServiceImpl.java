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
import com.easycommerce.response.DataResponse;
import com.easycommerce.user.User;
import com.easycommerce.user.UserService;
import com.easycommerce.user.address.Address;
import com.easycommerce.user.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public DataResponse<OrderDTO> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        User user = userService.getLoggedInUser();
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = orderRepository.findByUser(user, pageable);

        return buildDataResponse(page, sortBy, sortOrder);
    }

    @Override
    public OrderDTO placeOrder(Long addressId) {
        User user = userService.getLoggedInUser();
        Cart cart = user.getCart();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        Order order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setAddress(address);

        Order savedOrder = orderRepository.save(order);
        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems == null || cartItems.isEmpty())
            throw new APIException("Cart is empty");

        List<OrderItem> orderItems = cartItems
                .stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setDiscount(item.getDiscount());
                    orderItem.setProduct(item.getProduct());
                    orderItem.setOrder(savedOrder);
                    orderItem.setTotalPrice(item.getTotalPrice());
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

    private Sort getSort(String sortBy, String sortOrder) {
        return sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
    }

    private DataResponse<OrderDTO> buildDataResponse(Page<Order> page, String sortBy, String sortOrder) {
        List<OrderDTO> orders = page.stream().map(
                        order -> modelMapper.map(order, OrderDTO.class))
                .toList();

        return DataResponse.<OrderDTO>builder()
                .data(orders)
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
