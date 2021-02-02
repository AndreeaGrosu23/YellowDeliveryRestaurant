package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

        Optional<ShoppingCart> findShoppingCartByUser(User user);
}