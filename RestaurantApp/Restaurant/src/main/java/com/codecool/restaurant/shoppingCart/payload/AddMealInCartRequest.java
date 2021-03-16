package com.codecool.restaurant.shoppingCart.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AddMealInCartRequest {

    @NotNull
    private String idMeal;
    @NotEmpty
    private String mealName;
    @NotEmpty
    private String mealImage;
    @NotNull
    private double mealPrice;
    @NotNull
    private int quantity;




}
