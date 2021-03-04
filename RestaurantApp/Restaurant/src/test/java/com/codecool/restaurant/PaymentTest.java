package com.codecool.restaurant;

import com.codecool.restaurant.exception.NoDataFoundException;
import com.codecool.restaurant.exception.ShoppingCartException;
import com.codecool.restaurant.order.UserOrderRepository;
import com.codecool.restaurant.payment.PaymentDetailsModel;
import com.codecool.restaurant.payment.PaymentService;
import com.codecool.restaurant.shoppingCart.MealsInCartService;
import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentTest {

//    @Test
//    void testRegisterOrderThrowsExceptionWithEmptyCard() {
//
//        MealsInCartService mealsToCartService=mock(MealsInCartService.class);
//
//        UserService userService=mock(UserService.class);
//
//        UserOrderRepository userOrderRepository=mock(UserOrderRepository.class);
//
//        RestTemplate restTemplate=mock(RestTemplate.class);
//
//        //mock all services and put them in the constructor + when and then for their methods
//        PaymentService paymentService = new PaymentService(mealsToCartService, userService,  userOrderRepository, restTemplate);
//        PaymentDetailsModel paymentDetailsModel = new PaymentDetailsModel("j.doe", "success");
//
////        User user = new User("Jane", "Doe", "j.doe", "j.doe@gmail.com", "", "", "pass");
//        when(userService.getUserByUsername("j.doe")).thenThrow(new NoDataFoundException());
//
//        assertThrows(NoDataFoundException.class, () -> paymentService.registerOrder(paymentDetailsModel));
//    }
}

