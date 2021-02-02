package com.codecool.restaurant.payment;

import com.codecool.restaurant.meal.MealsToCartService;
import com.codecool.restaurant.payment.common.PaypalOrderModel;
import com.codecool.restaurant.shoppingCart.ShoppingCart;
import com.codecool.restaurant.exception.ShoppingCartException;
import com.codecool.restaurant.shoppingCart.ShoppingCartService;
import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import com.codecool.restaurant.order.UserOrder;
import com.codecool.restaurant.order.UserOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    private final MealsToCartService mealsToCartService;

    private final UserService userService;

    private final ShoppingCartService shoppingCartService;

    private final UserOrderRepository userOrderRepository;

    private final RestTemplate restTemplate;

    //As of Spring 4.3, classes with a single constructor can omit the @Autowired annotation
    public PaymentService(MealsToCartService mealsToCartService, UserService userService, ShoppingCartService shoppingCartService, UserOrderRepository userOrderRepository, RestTemplate restTemplate) {
        this.mealsToCartService = mealsToCartService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.userOrderRepository = userOrderRepository;
        this.restTemplate = restTemplate;
    }

    public void registerOrder(PaymentDetailsModel paymentDetailsModel) {
        String userName = paymentDetailsModel.getUserName();
        String paymentStatus = paymentDetailsModel.getPaymentStatus();

        User user = userService.getUserByUsername(userName);
        ShoppingCart shoppingCart = shoppingCartService.getCartByUser(user);

        double total = mealsToCartService.getTotalPrice(shoppingCart);

        UserOrder userOrder = new UserOrder(paymentStatus,total, user);

        userOrderRepository.save(userOrder);

        mealsToCartService.clearCart(shoppingCart);
    }


    public String requestPayment(PaypalOrderModel paypalOrderModel){
        String result;
        PaypalOrderModel response = restTemplate.postForObject("http://payment-service/api/v1/payment", paypalOrderModel, PaypalOrderModel.class);
        result=response.getLinkPaypal();
        return result;
    }
}
