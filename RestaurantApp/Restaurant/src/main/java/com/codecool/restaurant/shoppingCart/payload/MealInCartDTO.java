package com.codecool.restaurant.shoppingCart.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealInCartDTO {


    private Long mealInCartId;
    private String mealName;
    private String mealImage;
    private double mealPrice;
    private int quantity;
    private String idMeal;



}
