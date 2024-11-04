package com.easycommerce.order;

import com.easycommerce.order.orderitem.OrderItem;
import com.easycommerce.user.User;
import com.easycommerce.user.address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stripeSessionId;
    private boolean isPaid;

    @PositiveOrZero
    private double totalAmount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @Column(updatable = false)
    private LocalDateTime paymentDate;

    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<OrderItem> orderItems;

    @ManyToOne
    private Address address;

    @ManyToOne
    private User user;
}
