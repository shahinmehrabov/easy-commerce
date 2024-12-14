package com.easycommerce.order;

import com.easycommerce.response.DataResponse;
import jakarta.transaction.Transactional;

public interface OrderService {

    DataResponse<OrderDTO> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortOrder);

    @Transactional
    OrderDTO placeOrder(Long addressId);
}
