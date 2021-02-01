package com.codecool.restaurant.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsModel {

    @NotBlank(message= "Username cannot be empty")
    private String userName;

    @NotBlank(message= "Payment status cannot be empty")
    private String paymentStatus;
}
