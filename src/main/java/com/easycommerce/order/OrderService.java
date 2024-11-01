package com.easycommerce.order;

import jakarta.transaction.Transactional;

public interface OrderService {

    @Transactional
    OrderDTO placeOrder(Long addressId);
}
