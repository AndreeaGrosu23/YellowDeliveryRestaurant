package com.example.paymentservice.payment;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaypalController {

    private final PaypalService service;

    public static final String BASE_URL = "http://localhost:3000/payment/";
    public static final String SUCCESS_URL = BASE_URL + "success";
    public static final String CANCEL_URL = BASE_URL + "cancel";
    public static final String ERROR_URL = BASE_URL + "error";

    public PaypalController(PaypalService service) {
        this.service = service;
    }

    @PostMapping()
    public PaypalOrderModel payment(@RequestBody PaypalOrderModel paypalOrderModel) {
        log.info("MicroService");
        try {
            Payment payment = service.createPayment(paypalOrderModel.getTotalAmount(), paypalOrderModel.getDescription(), CANCEL_URL,
                    SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    paypalOrderModel.setLinkPaypal(link.getHref());
                    return paypalOrderModel;
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        paypalOrderModel.setLinkPaypal(ERROR_URL);
        return paypalOrderModel;
    }

}
