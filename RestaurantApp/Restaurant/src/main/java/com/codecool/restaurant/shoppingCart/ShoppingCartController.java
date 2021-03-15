package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.shoppingCart.payload.MealInCartDTO;
import com.codecool.restaurant.shoppingCart.payload.OrderDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RequestMapping("api/v1/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
public class ShoppingCartController {

    private final MealsInCartService mealsInCartService;

    @GetMapping
    public List<MealInCartDTO> getAllMealsInCart(Authentication authentication) {
        return mealsInCartService.allMealsInCartByAuthenticateUser(authentication);
    }

    @PostMapping
    public void addMealToCart(@Valid @RequestBody MealInCartDTO addMealToCartDTO, Authentication authentication) {
        mealsInCartService.addMealsToCart(addMealToCartDTO, authentication);
    }


    @PutMapping
    public void updateQtyMealInCart(@RequestBody MealInCartDTO meal) {
        if (meal.getQuantity() > 0) {
            mealsInCartService.changeQtyMealInCart(meal);
        } else {
            mealsInCartService.deleteItem(meal);
        }
    }

    @GetMapping("/order-details")
    public OrderDetailsDTO getOrderDetails(Authentication authentication) {
        return mealsInCartService.orderDetailsByAuthenticateUser(authentication);
    }


}