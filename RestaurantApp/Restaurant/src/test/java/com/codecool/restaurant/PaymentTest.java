package com.codecool.restaurant;

import com.codecool.restaurant.exception.ShoppingCartException;
import com.codecool.restaurant.meal.MealsToCartService;
import com.codecool.restaurant.payment.PaymentDetailsModel;
import com.codecool.restaurant.payment.PaymentService;
import com.codecool.restaurant.shoppingCart.ShoppingCartService;
import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import com.codecool.restaurant.order.UserOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentTest {

    @Test
    void testRegisterOrderThrowsExceptionWithEmptyCard() {

        MealsToCartService mealsToCartService=mock(MealsToCartService.class);

        UserService userService=mock(UserService.class);

        ShoppingCartService shoppingCartService=mock(ShoppingCartService.class);

        UserOrderRepository userOrderRepository=mock(UserOrderRepository.class);

        RestTemplate restTemplate=mock(RestTemplate.class);

        //mock all services and put them in the constructor + when and then for their methods
        PaymentService paymentService = new PaymentService(mealsToCartService, userService, shoppingCartService, userOrderRepository, restTemplate);
        PaymentDetailsModel paymentDetailsModel = new PaymentDetailsModel("j.doe", "success");

        User user = new User("Jane", "Doe", "j.doe", "j.doe@gmail.com", "", "", "pass");
        when(userService.getUserByUsername("j.doe")).thenReturn(user);
        when(shoppingCartService.getCartByUser(user)).thenThrow(new ShoppingCartException());

        assertThrows(ShoppingCartException.class, () -> paymentService.registerOrder(paymentDetailsModel));
    }
}

