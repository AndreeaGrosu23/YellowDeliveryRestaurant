package com.codecool.restaurant;

import com.codecool.restaurant.shoppingCart.ShoppingCartController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTestShoppingCartController {
//    A smoke test is basically just a sanity check to see
//    if the software functions on the most basic level.

    @Autowired
    private ShoppingCartController controller;


    @Test
    public void contextLoads(){
        assertThat(controller).isNotNull();
    }
}
