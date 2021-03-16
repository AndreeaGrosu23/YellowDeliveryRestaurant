package com.codecool.restaurant.shoppingCart.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralResponse {

    private boolean success;
    private String message;

}
