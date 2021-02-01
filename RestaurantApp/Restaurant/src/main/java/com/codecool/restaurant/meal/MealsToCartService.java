package com.codecool.restaurant.meal;

import com.codecool.restaurant.shoppingCart.ShoppingCart;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public List<MealsToCart> getAllMealsByCart(ShoppingCart shoppingCart) {

        return mealsToCartRepository.findAllByShoppingCartId(shoppingCart.getId());
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
}
