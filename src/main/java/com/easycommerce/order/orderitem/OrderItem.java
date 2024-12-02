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

    @Column(nullable = false)
    private Integer productQuantity;

    @Column(nullable = false)
    private Double discount;

    @Column(nullable = false)
    private Double totalPrice;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = NumberUtil.roundNumber(totalPrice);
    }
}
