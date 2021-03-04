package com.codecool.restaurant.meal;

import com.codecool.restaurant.shoppingCart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealsToCartRepository extends JpaRepository<MealsToCart, Long> {
    List<MealsToCart> findAllByShoppingCartId(Long id);

    boolean existsByIdMealAndShoppingCartId(String idMeal, Long id);

    MealsToCart findByIdMealAndShoppingCartId(String idMeal, Long id);

    @Query(value= "SELECT SUM(mc.quantity) FROM MealsToCart mc WHERE mc.shoppingCart=:shoppingCart")
    double totalQty(ShoppingCart shoppingCart);

    void deleteAllByShoppingCartId(Long id);
}
