package com.codecool.restaurant.payment;

import com.codecool.restaurant.payment.common.PaypalOrderModel;
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
    public String handleOrder (@Valid @RequestBody PaymentDetailsModel paymentDetailsModel)
    {
        paymentService.registerOrder(paymentDetailsModel);
        return "Order was handled!";
    }


}

