package com.easycommerce.order.orderitem;

import com.easycommerce.order.Order;
import com.easycommerce.product.Product;
import com.easycommerce.util.NumberUtil;
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
    private long productQuantity;

    @PositiveOrZero
    private double discount;

    @PositiveOrZero
    private double totalPrice;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = NumberUtil.roundPrice(totalPrice);
    }
}
