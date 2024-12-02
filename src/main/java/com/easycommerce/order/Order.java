package com.easycommerce.order;

import com.easycommerce.order.orderitem.OrderItem;
import com.easycommerce.user.User;
import com.easycommerce.user.address.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stripeSessionId;
    private boolean isPaid;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @Column(updatable = false)
    private LocalDateTime paymentDate;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @ManyToOne
    private Address address;

    @ManyToOne
    private User user;
}
