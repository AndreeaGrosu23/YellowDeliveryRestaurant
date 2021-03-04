package com.codecool.restaurant.meal;

import com.codecool.restaurant.shoppingCart.ShoppingCart;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="MEALS_TO_CART")
public class MealsToCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String mealName;
    private String mealImage;
    private double mealPrice;
    private int quantity;
    private String idMeal;

    @ManyToOne
    @JoinColumn(name="shoppingCart_id")
    private ShoppingCart shoppingCart;

}
