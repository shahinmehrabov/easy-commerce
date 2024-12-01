package com.easycommerce.order;

import com.easycommerce.order.orderitem.OrderItemDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private Long id;
    private boolean isPaid;

    @PositiveOrZero(message = "Total price must be equal or greater than 0")
    private double totalPrice;

    private LocalDateTime orderDate;
    private List<OrderItemDTO> orderItems;

    @NotNull(message = "Address is required")
    private Long addressId;
}
