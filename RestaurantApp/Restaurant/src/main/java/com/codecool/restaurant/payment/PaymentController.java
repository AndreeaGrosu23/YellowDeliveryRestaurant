package com.codecool.restaurant.payment;

import com.codecool.restaurant.payment.common.PaypalOrderModel;
import com.codecool.restaurant.exception.ShoppingCartException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/request-payment")
    public String requestPayment(@Valid @RequestBody PaypalOrderModel paypalOrderModel){
        return paymentService.requestPayment(paypalOrderModel);
    }

    @PostMapping
    public String getPaymentDetails(@Valid @RequestBody PaymentDetailsModel paymentDetailsModel)
    {
        try {
            paymentService.registerOrder(paymentDetailsModel);
        } catch (ShoppingCartException e) {
            return "Username does not exist!";
        }
        return "Success!";

    }
}
