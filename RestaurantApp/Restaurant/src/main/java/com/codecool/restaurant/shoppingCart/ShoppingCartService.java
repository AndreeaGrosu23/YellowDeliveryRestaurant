package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.exception.NoDataFoundException;
import com.codecool.restaurant.shoppingCart.payload.AddMealInCartRequest;
import com.codecool.restaurant.shoppingCart.payload.ChangeQtyInCartRequest;
import com.codecool.restaurant.shoppingCart.payload.OrderDetailsDTO;
import com.codecool.restaurant.shoppingCart.payload.ViewCart;
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

    public ShoppingCart addMealsToCart(AddMealInCartRequest addMealToCartDTO, Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);
        Long userId = user.getId();
        String idMeal = addMealToCartDTO.getIdMeal();

        boolean exists = shoppingCartRepository.existsByIdMealAndUserId(idMeal, userId);

        if (!exists) {
            ShoppingCart shoppingCart = new ShoppingCart();

            shoppingCart.setUser(user);
            shoppingCart.setIdMeal(idMeal);
            shoppingCart.setMealImage(addMealToCartDTO.getMealImage());
            shoppingCart.setQuantity(addMealToCartDTO.getQuantity());
            shoppingCart.setMealName(addMealToCartDTO.getMealName());
            shoppingCart.setMealPrice(addMealToCartDTO.getMealPrice());

            return shoppingCartRepository.save(shoppingCart);
        } else {
            ShoppingCart meal = shoppingCartRepository.findByIdMealAndUserId(idMeal, userId).orElseThrow(NoDataFoundException::new);
            meal.setQuantity(meal.getQuantity() + 1);
            return shoppingCartRepository.save(meal);
        }
    }


    public double getTotalPrice(User user) {
        return shoppingCartRepository.totalQty(user) * 5;
    }


    public List<ViewCart> allMealsInCartByAuthenticateUser(Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);
        Long userId = user.getId();
        List<ShoppingCart> allByUserId = shoppingCartRepository.findAllByUserIdOrderById(userId);

        List<ViewCart> mealsInCart = new ArrayList<>();

        allByUserId.forEach(item -> {
            mealsInCart.add(new ViewCart(item.getIdMeal(),
                    item.getMealName(),
                    item.getMealImage(),
                    item.getMealPrice(),
                    item.getQuantity(),
                    item.getId()));
        });
        return mealsInCart;
    }

    public ShoppingCart changeQtyMealInCart(ChangeQtyInCartRequest meal) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(meal.getMealInCartId()).orElseThrow(NoDataFoundException::new);
        shoppingCart.setQuantity(meal.getQuantity());
        return shoppingCartRepository.save(shoppingCart);
    }

    public void deleteItem(ChangeQtyInCartRequest meal) {
        shoppingCartRepository.deleteById(meal.getMealInCartId());

    }

    public OrderDetailsDTO orderDetailsByAuthenticateUser(Authentication authentication) {
        String authenticationName = authentication.getName();
        User user = userService.getUserByUsername(authenticationName);

        List<ViewCart> cartProducts = allMealsInCartByAuthenticateUser(authentication);
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
