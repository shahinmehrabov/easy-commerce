package com.easycommerce.product;

import com.easycommerce.category.Category;
import com.easycommerce.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min = 3, message = "Product name must contain at least 3 characters")
    private String name;

    @NotBlank
    @Size(min = 5, message = "Product description must contain at least 5 characters")
    private String description;
    private String image;
    private int quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    public void setSpecialPrice() {
        specialPrice = price * (discount * 0.01);
    }
}
