package com.codecool.restaurant.meal;

import com.codecool.restaurant.shoppingCart.ShoppingCart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="MEALS_TO_CART")
public class MealsToCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity = 1;

    @ManyToOne
    @JoinColumn(name="shoppingCart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name="meal_id")
    private Meal meal;



    public MealsToCart(ShoppingCart shoppingCart, Meal meal) {
        this.shoppingCart = shoppingCart;
        this.meal = meal;
    }
}
