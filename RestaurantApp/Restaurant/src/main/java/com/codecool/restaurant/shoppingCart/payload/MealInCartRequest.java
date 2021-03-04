package com.codecool.restaurant.shoppingCart.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealInCartRequest {

    @NotEmpty
    private Long mealInCartId;
    private String mealName;
    private String mealImage;
    private double mealPrice;
    private int quantity;


}
