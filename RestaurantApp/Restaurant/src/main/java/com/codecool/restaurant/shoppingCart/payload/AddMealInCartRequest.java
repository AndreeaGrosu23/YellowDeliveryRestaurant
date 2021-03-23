package com.codecool.restaurant.shoppingCart.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class AddMealInCartRequest {
/*
      Tutorial: https://www.baeldung.com/java-bean-validation-not-null-empty-blank
      @NotNull constraint won't allow null values for the constrained field(s). Even so, the field(s) can be empty.
      @NotEmpty must be not null and its size/length must be greater than zero.
      @NotBlank must be not null and the trimmed length must be greater than zero

 */
    @NotEmpty
    private String idMeal;
    @NotEmpty
    private String mealName;
    @NotEmpty
    private String mealImage;
    @DecimalMin(value = "5.00")
    private double mealPrice;
    @Min(value = 1, message = "Quantity should not be less than {value}")
    private int quantity;

}
