package com.easycommerce.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/cart/order/address/id/{addressId}")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable Long addressId) {
        OrderDTO orderDTO = orderService.placeOrder(addressId);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
}
