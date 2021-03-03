package com.codecool.restaurant.shoppingCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsRequest {

    List<MealInCartRequest> meals;
    private String userFirstName;
    private String userLastName;
    private String userPhoneNumber;
    private String userEmailAddress;
    private String userDeliveryAddress;



}
