package com.codecool.restaurant.shoppingCart.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor

public class ViewCart {

    private String idMeal;

    private String mealName;

    private String mealImage;

    private double mealPrice;

    private int quantity;

    private Long mealInCartId;
}
