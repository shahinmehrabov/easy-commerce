package com.easycommerce.order.orderitem;

import com.easycommerce.order.Order;
import com.easycommerce.product.Product;
import com.easycommerce.util.NumberUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long productQuantity;
    private double discount;
    private double totalPrice;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = NumberUtil.roundNumber(totalPrice);
    }
}
