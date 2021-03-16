package com.codecool.restaurant.payment;

import com.codecool.restaurant.shoppingCart.ShoppingCartService;
import com.codecool.restaurant.payment.common.PaypalOrderModel;
import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import com.codecool.restaurant.order.UserOrder;
import com.codecool.restaurant.order.UserOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class PaymentService {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final UserOrderRepository userOrderRepository;
    private final RestTemplate restTemplate;


    public void registerOrder(PaymentDetailsModel paymentDetailsModel) {
        String userName = paymentDetailsModel.getUserName();
        String paymentStatus = paymentDetailsModel.getPaymentStatus();

//        Save status order
        User user = userService.getUserByUsername(userName);
        double total = shoppingCartService.getTotalPrice(user);
        UserOrder userOrder = new UserOrder(paymentStatus, total, user);
        userOrderRepository.save(userOrder);
//        Clear the shopping cart
        shoppingCartService.clearCart(user);
    }


    public String requestPayment(PaypalOrderModel paypalOrderModel) {
        String result;
        PaypalOrderModel response = restTemplate.postForObject("http://payment-service/api/v1/payment", paypalOrderModel, PaypalOrderModel.class);
        result = response.getLinkPaypal();
        return result;
    }
}
