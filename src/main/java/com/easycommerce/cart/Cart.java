package com.easycommerce.cart;

import com.easycommerce.cart.cartitem.CartItem;
import com.easycommerce.user.User;
import com.easycommerce.util.NumberUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double totalPrice;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<CartItem> cartItems;

    @OneToOne
    private User user;

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = NumberUtil.roundNumber(totalPrice);
    }
}
