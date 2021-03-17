package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.shoppingCart.payload.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public List<ViewCart> getAllMealsInCart(Authentication authentication) {
        return shoppingCartService.allMealsInCartByAuthenticateUser(authentication);
    }

    @PostMapping
    public ResponseEntity<?> addMealToCart(@Valid @RequestBody AddMealInCartRequest addMealToCartDTO, Authentication authentication) {
        ShoppingCart shoppingCart = shoppingCartService.addMealsToCart(addMealToCartDTO, authentication);
        return ResponseEntity.ok(new GeneralResponse(true, "Meal add to cart " + shoppingCart.getMealName()));
    }


    @PutMapping
    public ResponseEntity<?> updateQtyMealInCart(@Valid @RequestBody ChangeQtyInCartRequest meal) {
        if (meal.getQuantity() > 0) {
            ShoppingCart shoppingCart = shoppingCartService.changeQtyMealInCart(meal);
            return ResponseEntity.ok(new GeneralResponse(true, "Quantity change " + shoppingCart.getMealName()));
        } else {
            shoppingCartService.deleteItem(meal);
            return ResponseEntity.ok(new GeneralResponse(true, "Meal removed"));
        }
    }

    @GetMapping("/order-details")
    public OrderDetailsDTO getOrderDetails(Authentication authentication) {
        return shoppingCartService.orderDetailsByAuthenticateUser(authentication);
    }


}