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

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public List<MealInCartDTO> getAllMealsInCart(Authentication authentication) {
        return shoppingCartService.allMealsInCartByAuthenticateUser(authentication);
    }

    @PostMapping
    public void addMealToCart(@Valid @RequestBody MealInCartDTO addMealToCartDTO, Authentication authentication) {
        shoppingCartService.addMealsToCart(addMealToCartDTO, authentication);
    }


    @PutMapping
    public void updateQtyMealInCart(@RequestBody MealInCartDTO meal) {
        if (meal.getQuantity() > 0) {
            shoppingCartService.changeQtyMealInCart(meal);
        } else {
            shoppingCartService.deleteItem(meal);
        }
    }

    @GetMapping("/order-details")
    public OrderDetailsDTO getOrderDetails(Authentication authentication) {
        return shoppingCartService.orderDetailsByAuthenticateUser(authentication);
    }


}