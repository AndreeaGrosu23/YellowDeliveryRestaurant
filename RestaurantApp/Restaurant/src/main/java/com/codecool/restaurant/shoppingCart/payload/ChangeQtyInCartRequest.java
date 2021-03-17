package com.codecool.restaurant.shoppingCart.payload;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Data
public class ChangeQtyInCartRequest {

    @NotNull
    private Long mealInCartId;

    @NotNull
    @Digits(integer = 1, fraction = 0)
    private int quantity;
}
