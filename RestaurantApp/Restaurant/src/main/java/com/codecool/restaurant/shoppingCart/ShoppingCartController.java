package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.meal.Meal;
import com.codecool.restaurant.meal.MealService;
import com.codecool.restaurant.meal.MealsToCart;
import com.codecool.restaurant.meal.MealsToCartService;
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

    @PostMapping
    public void addMealToDB(@Valid @RequestBody AddMealToCart addMealToCart) {
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

    @GetMapping(path = "mealsInCart/{id}")
    public Map<Meal, Integer> getAllMealInCart(@PathVariable("id") Long id) {
        ShoppingCart cart = shoppingCartService.getCartById(id);
        List<MealsToCart> list = mealsToCartService.getAllMealsByCart(cart);
        Map<Meal, Integer> listOfMeals = new HashMap<>();
        for (MealsToCart meal : list) {
            listOfMeals.put(meal.getMeal(), meal.getQuantity());
        }
        return listOfMeals;
    }

    @GetMapping
    public List< MealInCartRequest> getCartMeals(Authentication authentication){
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);
        ShoppingCart cartByUser = shoppingCartService.getCartByUser(user);
        return mealsToCartService.mealsInCart(cartByUser);
    }

    @PutMapping
    public void updateQtyMealInCart(@RequestBody MealInCartRequest meal){
        mealsToCartService.changeQtyMealInCart(meal);
    }

    @GetMapping("/order-details")
    public OrderDetailsRequest getOrderDetails(Authentication authentication){
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


    @GetMapping(path = "view/{username}")
    public long getCartIdByUserName(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        return shoppingCartService.getCartByUser(user).getId();
    }
}