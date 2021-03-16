package com.codecool.restaurant.shoppingCart.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {

    List<ViewCart> meals;
    private String userFirstName;
    private String userLastName;
    private String userPhoneNumber;
    private String userEmailAddress;
    private String userDeliveryAddress;



}
