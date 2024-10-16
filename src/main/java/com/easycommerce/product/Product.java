package com.easycommerce.product;

import com.easycommerce.category.Category;
import com.easycommerce.user.User;
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

    @Min(value = 3)
    @Column(nullable = false)
    private String name;

    @Min(value = 5)
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
    private double specialPrice;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public void setSpecialPrice() {
        specialPrice = price * (discount * 0.01);
    }
}
