package com.codecool.restaurant.meal;

import com.codecool.restaurant.exception.NoDataFoundException;
import com.codecool.restaurant.shoppingCart.payload.MealInCartRequest;
import com.codecool.restaurant.shoppingCart.ShoppingCart;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealsToCartService {
    private final MealsToCartRepository mealsToCartRepository;

    public MealsToCartService(MealsToCartRepository mealsToCartRepository) {
        this.mealsToCartRepository = mealsToCartRepository;
    }

    public void addMealsToCart(MealsToCart mealsToCart){
        mealsToCartRepository.save(mealsToCart);
    }


    @Transactional
    public void updateQuantity(Meal meal) {
        mealsToCartRepository.updateQuantity(meal);
    }

    public double getTotalPrice(ShoppingCart shoppingCart) {
        return mealsToCartRepository.totalQty(shoppingCart) * 5;
    }

    @Transactional
    public void clearCart(ShoppingCart shoppingCart) {
        mealsToCartRepository.deleteByShoppingCart(shoppingCart);
    }

    public List<MealInCartRequest> mealsInCart(ShoppingCart cartByUser){
        Long cartId = cartByUser.getId();
        List<MealsToCart> allByShoppingCartId = mealsToCartRepository.findAllByShoppingCartId(cartId);
        List<MealInCartRequest> result = new ArrayList<>();
        allByShoppingCartId.forEach(item -> {
            MealInCartRequest meal = new MealInCartRequest();
            meal.setMealInCartId(item.getId());
            meal.setMealName(item.getMeal().getName());
            meal.setMealImage(item.getMeal().getImage());
            meal.setMealPrice(item.getMeal().getPrice());
            meal.setQuantity(item.getQuantity());
            result.add(meal);
        });
        return result;
    }

    public void changeQtyMealInCart(MealInCartRequest meal) {
        MealsToCart mealsToCart = mealsToCartRepository.findById(meal.getMealInCartId()).orElseThrow(NoDataFoundException::new);
        mealsToCart.setQuantity(meal.getQuantity());
        mealsToCartRepository.save(mealsToCart);
    }

    public void deleteItem(MealInCartRequest meal) {
        mealsToCartRepository.deleteById(meal.getMealInCartId());
    }
}
