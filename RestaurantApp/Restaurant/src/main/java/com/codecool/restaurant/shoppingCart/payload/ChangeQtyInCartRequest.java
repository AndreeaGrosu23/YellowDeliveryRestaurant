package com.codecool.restaurant.shoppingCart.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeQtyInCartRequest {

    @NotNull
    private Long mealInCartId;

    @NotNull
    private int quantity;
}
