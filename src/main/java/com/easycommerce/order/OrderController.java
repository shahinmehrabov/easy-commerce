package com.easycommerce.order;

import com.easycommerce.config.AppConstants;
import com.easycommerce.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<DataResponse<OrderDTO>> getAllOrders(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {

        DataResponse<OrderDTO> dataResponse = orderService.getAllOrders(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @PostMapping("/cart/order/address/id/{addressId}")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable Long addressId) {
        OrderDTO orderDTO = orderService.placeOrder(addressId);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
}
