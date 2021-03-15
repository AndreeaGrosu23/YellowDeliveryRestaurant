package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.exception.NoDataFoundException;
import com.codecool.restaurant.shoppingCart.payload.MealInCartDTO;
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
public class MealsInCartService {
    private final MealsInCartRepository mealsInCartRepository;
    private final UserService userService;

    public void addMealsToCart(MealInCartDTO addMealToCartDTO, Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);

        boolean exists = mealsInCartRepository.existsByIdMealAndUserId(addMealToCartDTO.getIdMeal(), user.getId());

        if (!exists) {
            MealsInCart mealsInCart = new MealsInCart();

            mealsInCart.setUser(user);
            mealsInCart.setIdMeal(addMealToCartDTO.getIdMeal());
            mealsInCart.setMealImage(addMealToCartDTO.getMealImage());
            mealsInCart.setQuantity(addMealToCartDTO.getQuantity());
            mealsInCart.setMealName(addMealToCartDTO.getMealName());
            mealsInCart.setMealPrice(addMealToCartDTO.getMealPrice());


            mealsInCartRepository.save(mealsInCart);
        } else {
            MealsInCart meal = mealsInCartRepository.findByIdMealAndUserId(addMealToCartDTO.getIdMeal(), user.getId());
            meal.setQuantity(meal.getQuantity() + 1);
            mealsInCartRepository.save(meal);
        }
    }


    public double getTotalPrice(User user) {
        return mealsInCartRepository.totalQty(user) * 5;
    }


    public List<MealInCartDTO> allMealsInCartByAuthenticateUser(Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);
        List<MealsInCart> allByShoppingCartId = mealsInCartRepository.findAllByUserId(user.getId());
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
        MealsInCart mealsInCart = mealsInCartRepository.findById(meal.getMealInCartId()).orElseThrow(NoDataFoundException::new);
        mealsInCart.setQuantity(meal.getQuantity());
        mealsInCartRepository.save(mealsInCart);
    }

    public void deleteItem(MealInCartDTO meal) {
        mealsInCartRepository.deleteById(meal.getMealInCartId());
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
    public void clearCart(User user) {
        mealsInCartRepository.deleteAllByUserId(user.getId());
    }
}
