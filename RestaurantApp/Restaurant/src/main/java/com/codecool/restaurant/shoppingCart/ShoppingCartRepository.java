package com.codecool.restaurant.shoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

        @Query("SELECT c FROM ShoppingCart c JOIN  c.user u WHERE u.userName = ?1")
        ShoppingCart findShoppingcartByUsername(String username);
}