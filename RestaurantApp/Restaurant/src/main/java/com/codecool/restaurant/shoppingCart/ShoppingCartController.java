package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.meal.Meal;
import com.codecool.restaurant.meal.MealService;
import com.codecool.restaurant.meal.MealsToCart;
import com.codecool.restaurant.meal.MealsToCartService;
import com.codecool.restaurant.shoppingCart.payload.AddMealToCart;
import com.codecool.restaurant.shoppingCart.payload.MealInCartRequest;
import com.codecool.restaurant.shoppingCart.payload.OrderDetailsRequest;
import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RequestMapping("api/v1/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final MealService mealService;
    private final UserService userService;
    private final MealsToCartService mealsToCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, MealService mealService, UserService userService, MealsToCartService mealsToCartService) {
        this.shoppingCartService = shoppingCartService;
        this.mealService = mealService;
        this.userService = userService;
        this.mealsToCartService = mealsToCartService;
    }

    @GetMapping
    public List<MealInCartRequest> getCartMeals(Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);
        ShoppingCart cartByUser = shoppingCartService.getCartByUser(user);
        return mealsToCartService.mealsInCart(cartByUser);
    }

    @PostMapping
    public void addMealToDB(@Valid @RequestBody AddMealToCart addMealToCart) {

        System.out.println(addMealToCart);
        if (mealService.findByName(addMealToCart.getMealName()) != null) {
            mealsToCartService.updateQuantity(mealService.findByName(addMealToCart.getMealName()));
        } else {
            Meal meal = new Meal(addMealToCart.getMealName(), "https://www.themealdb.com/images/media/meals/" + addMealToCart.getImage());
            mealService.addMeal(meal);
            User user = userService.getUserByUsername(addMealToCart.getUsername());
            ShoppingCart cart = shoppingCartService.getCartByUser(user);
            mealsToCartService.addMealsToCart(new MealsToCart(cart, meal));
        }
    }


    @PutMapping
    public void updateQtyMealInCart(@RequestBody MealInCartRequest meal) {
        if(meal.getQuantity() > 0){
            mealsToCartService.changeQtyMealInCart(meal);
        }else {
            mealsToCartService.deleteItem(meal);
        }
    }


    @GetMapping("/order-details")
    public OrderDetailsRequest getOrderDetails(Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);
        List<MealInCartRequest> cartProducts = getCartMeals(authentication);

        OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest();

        orderDetailsRequest.setMeals(cartProducts);
        orderDetailsRequest.setUserDeliveryAddress(user.getDeliveryAddress());
        orderDetailsRequest.setUserEmailAddress(user.getEmailAddress());
        orderDetailsRequest.setUserFirstName(user.getFirstName());
        orderDetailsRequest.setUserLastName(user.getLastName());
        orderDetailsRequest.setUserPhoneNumber(user.getPhoneNumber());

        return orderDetailsRequest;
    }

}