package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.meal.Meal;
import com.codecool.restaurant.meal.MealService;
import com.codecool.restaurant.meal.MealsToCart;
import com.codecool.restaurant.meal.MealsToCartService;
import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
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

//    @PostMapping(path = "{username}/meal/{mealName}/tocart/{image}")
//    public void addMealToDB(@PathVariable("username") String username, @PathVariable("mealName") String mealName, @PathVariable("image") String image){
//
//        if (mealService.findByName(mealName) != null) {
//            mealsToCartService.updateQuantity(mealService.findByName(mealName));
//        } else {
//            Meal meal = new Meal(mealName, "https://www.themealdb.com/images/media/meals/" + image);
//            mealService.addMeal(meal);
//            User user = userService.getUserByUsername(username);
//            ShoppingCart cart = shoppingCartService.getCartByUser(user);
//            mealsToCartService.addMealsToCart(new MealsToCart(cart, meal));
//        }
//    }
@PostMapping("/meal")
public void addMealToDB(@Valid @RequestBody AddMealToCart addMealToCart){
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

    @GetMapping(path="mealsInCart/{id}")
    public Map<Meal, Integer> getAllMealInCart(@PathVariable("id") Long id){
        ShoppingCart cart = shoppingCartService.getCartById(id);
        List<MealsToCart> list = mealsToCartService.getAllMealsByCart(cart);
        Map<Meal, Integer> listOfMeals = new HashMap<>();
        for (MealsToCart meal: list) {
            listOfMeals.put(meal.getMeal(),meal.getQuantity());
        }
        return listOfMeals;
    }

    @GetMapping(path="view/{username}")
    public long getCartIdByUserName(@PathVariable("username") String username){
          User user = userService.getUserByUsername(username);
          return shoppingCartService.getCartByUser(user).getId();
    }
}