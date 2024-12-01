package com.easycommerce.product;

import com.easycommerce.category.Category;
import com.easycommerce.user.User;
import com.easycommerce.util.NumberUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageName;

    private int quantity;
    private double price;
    private double discount;
    private double priceAfterDiscount;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    public void setPrice(double price) {
        this.price = NumberUtil.roundNumber(price);
    }

    public void setPriceAfterDiscount() {
        priceAfterDiscount = NumberUtil.roundNumber(price - (price * (discount * 0.01)));
    }
}
