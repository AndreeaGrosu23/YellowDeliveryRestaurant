package com.codecool.restaurant.meal;

import com.codecool.restaurant.exception.NoDataFoundException;
import com.codecool.restaurant.shoppingCart.ShoppingCartService;
import com.codecool.restaurant.shoppingCart.payload.AddMealToCartDTO;
import com.codecool.restaurant.shoppingCart.payload.MealInCartDTO;
import com.codecool.restaurant.shoppingCart.ShoppingCart;
import com.codecool.restaurant.shoppingCart.payload.OrderDetailsDTO;
import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MealsToCartService {
    private final MealsToCartRepository mealsToCartRepository;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;


    public void addMealsToCart(AddMealToCartDTO addMealToCartDTO, Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);
        ShoppingCart cartByUser = shoppingCartService.getCartByUser(user);
        boolean exists = mealsToCartRepository.existsByIdMealAndShoppingCartId(addMealToCartDTO.getIdMeal(), cartByUser.getId());

        if (!exists) {
            MealsToCart mealsToCart = new MealsToCart();

            mealsToCart.setIdMeal(addMealToCartDTO.getIdMeal());
            mealsToCart.setShoppingCart(cartByUser);
            mealsToCart.setMealImage(addMealToCartDTO.getMealImage());
            mealsToCart.setQuantity(addMealToCartDTO.getQuantity());
            mealsToCart.setMealName(addMealToCartDTO.getMealName());
            mealsToCart.setMealPrice(addMealToCartDTO.getMealPrice());

            mealsToCartRepository.save(mealsToCart);
        } else {
            MealsToCart meal = mealsToCartRepository.findByIdMealAndShoppingCartId(addMealToCartDTO.getIdMeal(), cartByUser.getId());
            meal.setQuantity(meal.getQuantity() + 1);
            mealsToCartRepository.save(meal);
        }
    }


    public double getTotalPrice(ShoppingCart shoppingCart) {
        return mealsToCartRepository.totalQty(shoppingCart) * 5;
    }


    public List<MealInCartDTO> allMealsInCartByAuthenticateUser(Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);
        ShoppingCart cartByUser = shoppingCartService.getCartByUser(user);
        List<MealsToCart> allByShoppingCartId = mealsToCartRepository.findAllByShoppingCartId(cartByUser.getId());
        List<MealInCartDTO> mealsInCart = new ArrayList<>();
        allByShoppingCartId.forEach(item -> {
            MealInCartDTO meal = new MealInCartDTO();
            meal.setMealInCartId(item.getId());
            meal.setMealName(item.getMealName());
            meal.setMealImage(item.getMealImage());
            meal.setMealPrice(item.getMealPrice());
            meal.setQuantity(item.getQuantity());
            mealsInCart.add(meal);
        });
        return mealsInCart;
    }

    public void changeQtyMealInCart(MealInCartDTO meal) {
        MealsToCart mealsToCart = mealsToCartRepository.findById(meal.getMealInCartId()).orElseThrow(NoDataFoundException::new);
        mealsToCart.setQuantity(meal.getQuantity());
        mealsToCartRepository.save(mealsToCart);
    }

    public void deleteItem(MealInCartDTO meal) {
        mealsToCartRepository.deleteById(meal.getMealInCartId());
    }

    public OrderDetailsDTO orderDetailsByAuthenticateUser(Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);

        List<MealInCartDTO> cartProducts = allMealsInCartByAuthenticateUser(authentication);
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();

        orderDetailsDTO.setMeals(cartProducts);
        orderDetailsDTO.setUserDeliveryAddress(user.getDeliveryAddress());
        orderDetailsDTO.setUserEmailAddress(user.getEmailAddress());
        orderDetailsDTO.setUserFirstName(user.getFirstName());
        orderDetailsDTO.setUserLastName(user.getLastName());
        orderDetailsDTO.setUserPhoneNumber(user.getPhoneNumber());

        return orderDetailsDTO;
    }

    @Transactional
    public void clearCart(ShoppingCart shoppingCart) {
        mealsToCartRepository.deleteAllByShoppingCartId(shoppingCart.getId());
    }
}
