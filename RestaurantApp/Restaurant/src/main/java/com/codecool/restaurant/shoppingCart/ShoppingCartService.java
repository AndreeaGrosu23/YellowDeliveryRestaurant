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
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;

    public void addMealsToCart(MealInCartDTO addMealToCartDTO, Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);

        boolean exists = shoppingCartRepository.existsByIdMealAndUserId(addMealToCartDTO.getIdMeal(), user.getId());

        if (!exists) {
            ShoppingCart shoppingCart = new ShoppingCart();

            shoppingCart.setUser(user);
            shoppingCart.setIdMeal(addMealToCartDTO.getIdMeal());
            shoppingCart.setMealImage(addMealToCartDTO.getMealImage());
            shoppingCart.setQuantity(addMealToCartDTO.getQuantity());
            shoppingCart.setMealName(addMealToCartDTO.getMealName());
            shoppingCart.setMealPrice(addMealToCartDTO.getMealPrice());


            shoppingCartRepository.save(shoppingCart);
        } else {
            ShoppingCart meal = shoppingCartRepository.findByIdMealAndUserId(addMealToCartDTO.getIdMeal(), user.getId());
            meal.setQuantity(meal.getQuantity() + 1);
            shoppingCartRepository.save(meal);
        }
    }


    public double getTotalPrice(User user) {
        return shoppingCartRepository.totalQty(user) * 5;
    }


    public List<MealInCartDTO> allMealsInCartByAuthenticateUser(Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);
        List<ShoppingCart> allByShoppingCartId = shoppingCartRepository.findAllByUserId(user.getId());
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
        ShoppingCart shoppingCart = shoppingCartRepository.findById(meal.getMealInCartId()).orElseThrow(NoDataFoundException::new);
        shoppingCart.setQuantity(meal.getQuantity());
        shoppingCartRepository.save(shoppingCart);
    }

    public void deleteItem(MealInCartDTO meal) {
        shoppingCartRepository.deleteById(meal.getMealInCartId());
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
        shoppingCartRepository.deleteAllByUserId(user.getId());
    }
}
