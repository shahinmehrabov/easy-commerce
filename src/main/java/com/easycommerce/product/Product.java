package com.easycommerce.product;

import com.easycommerce.category.Category;
import com.easycommerce.user.User;
import com.easycommerce.util.NumberUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3)
    @Column(nullable = false)
    private String name;

    @Size(min = 5)
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageName;

    @PositiveOrZero
    private int quantity;

    @PositiveOrZero
    private double price;

    @PositiveOrZero
    private double discount;

    @PositiveOrZero
    private double priceAfterDiscount;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public void setPrice(double price) {
        this.price = NumberUtil.roundNumber(price);
    }

    public void setPriceAfterDiscount() {
        priceAfterDiscount = price - (price * (discount * 0.01));
        priceAfterDiscount = NumberUtil.roundNumber(priceAfterDiscount);
    }
}
