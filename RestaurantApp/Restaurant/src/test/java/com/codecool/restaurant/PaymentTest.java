package com.codecool.restaurant;

import com.codecool.restaurant.meal.MealsToCartService;
import com.codecool.restaurant.payment.PaymentDetailsModel;
import com.codecool.restaurant.payment.PaymentService;
import com.codecool.restaurant.shoppingCart.ShoppingCartService;
import com.codecool.restaurant.user.UserService;
import com.codecool.restaurant.order.UserOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

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
        PaymentDetailsModel paymentDetailsModel = new PaymentDetailsModel("Wrong username", "success");

        when(userService.getUserByUsername("Wrong username")).thenReturn(null);
        when(shoppingCartService.getCartByUser(null)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> paymentService.registerOrder(paymentDetailsModel));
    }
}

