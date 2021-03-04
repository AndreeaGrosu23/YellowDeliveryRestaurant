package com.codecool.restaurant.shoppingCart.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class AddMealToCart {

    @NotEmpty
    private String username;
    @NotEmpty
    private String mealName;
    @NotEmpty
    private String image;


}
