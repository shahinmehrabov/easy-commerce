package com.easycommerce.order.orderitem;

import com.easycommerce.order.Order;
import com.easycommerce.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero
    private long quantity;

    @PositiveOrZero
    private double discount;

    @PositiveOrZero
    private double orderPrice;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Order order;

    public void setOrderPrice() {
        orderPrice = product.getPriceAfterDiscount() * quantity;
        orderPrice = roundDouble(orderPrice);
    }

    private double roundDouble(double value) {
        return (double) Math.round(value * 100) / 100;
    }
}
