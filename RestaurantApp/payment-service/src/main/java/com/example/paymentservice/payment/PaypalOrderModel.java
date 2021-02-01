package com.example.paymentservice.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaypalOrderModel {

    private double totalAmount;
    private String description;
    private String linkPaypal;

}
