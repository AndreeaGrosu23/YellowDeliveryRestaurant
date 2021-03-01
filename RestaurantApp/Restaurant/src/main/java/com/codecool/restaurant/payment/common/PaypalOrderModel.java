package com.codecool.restaurant.payment.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaypalOrderModel {

    @NotNull(message="Total amount cannot be empty")
    @PositiveOrZero(message="Total amount has to be valid")
    private double totalAmount;

    @NotBlank(message="Description cannot be empty")
    private String description;

    private String linkPaypal;

}
