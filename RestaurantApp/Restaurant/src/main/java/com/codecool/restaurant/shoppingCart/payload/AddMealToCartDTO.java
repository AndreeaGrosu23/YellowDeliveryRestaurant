package com.codecool.restaurant.shoppingCart.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class AddMealToCartDTO {


    private String mealName;
    private String mealImage;
    private double mealPrice;
    private int quantity;
    private String idMeal;


}
