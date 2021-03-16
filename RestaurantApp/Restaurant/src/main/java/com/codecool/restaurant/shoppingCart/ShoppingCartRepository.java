package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAllByUserId(Long id);

    boolean existsByIdMealAndUserId(String idMeal, Long id);

    ShoppingCart findByIdMealAndUserId(String idMeal, Long id);

    @Query(value= "SELECT SUM(mc.quantity) FROM ShoppingCart mc WHERE mc.user=:user")
    double totalQty(User user);

    void deleteAllByUserId(Long id);
}
