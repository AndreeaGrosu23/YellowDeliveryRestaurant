package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.meal.MealsToCartService;
import com.codecool.restaurant.shoppingCart.payload.AddMealToCartDTO;
import com.codecool.restaurant.shoppingCart.payload.MealInCartDTO;
import com.codecool.restaurant.shoppingCart.payload.OrderDetailsDTO;
import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserController;
import com.codecool.restaurant.user.UserService;
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

    private final MealsToCartService mealsToCartService;


    @GetMapping
    public List<MealInCartDTO> getAllMealsInCart(Authentication authentication) {
        return mealsToCartService.allMealsInCartByAuthenticateUser(authentication);
    }

    @PostMapping
    public void addMealToCart(@Valid @RequestBody AddMealToCartDTO addMealToCartDTO, Authentication authentication) {
        mealsToCartService.addMealsToCart(addMealToCartDTO, authentication);
    }


    @PutMapping
    public void updateQtyMealInCart(@RequestBody MealInCartDTO meal) {
        if (meal.getQuantity() > 0) {
            mealsToCartService.changeQtyMealInCart(meal);
        } else {
            mealsToCartService.deleteItem(meal);
        }
    }

    @GetMapping("/order-details")
    public OrderDetailsDTO getOrderDetails(Authentication authentication) {
        return mealsToCartService.orderDetailsByAuthenticateUser(authentication);
    }


}