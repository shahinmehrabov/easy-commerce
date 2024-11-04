package com.easycommerce.order;

import com.easycommerce.order.orderitem.OrderItemDTO;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private boolean isPaid;

    @PositiveOrZero
    private double totalAmount;

    private LocalDateTime orderDate;
    private List<OrderItemDTO> orderItems;
    private Long addressId;
}
