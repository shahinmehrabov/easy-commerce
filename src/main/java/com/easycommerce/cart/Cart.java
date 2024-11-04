package com.easycommerce.cart;

import com.easycommerce.cart.cartitem.CartItem;
import com.easycommerce.user.User;
import com.easycommerce.util.NumberUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero
    private double totalPrice;

    @OneToMany(mappedBy = "cart",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<CartItem> cartItems;

    @OneToOne
    private User user;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = NumberUtil.roundPrice(totalPrice);
    }
}
