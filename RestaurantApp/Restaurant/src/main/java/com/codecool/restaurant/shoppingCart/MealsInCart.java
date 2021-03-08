package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.user.User;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "MEALS_IN_CART")
public class MealsInCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String mealName;
    private String mealImage;
    private double mealPrice;
    private int quantity;
    private String idMeal;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
