package com.codecool.restaurant.shoppingCart;

import com.codecool.restaurant.exception.ShoppingCartException;
import com.codecool.restaurant.user.User;
import org.springframework.stereotype.Service;


@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository){
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public void addCart(ShoppingCart shoppingCart){
        shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart getCartByUser(User user) {
        return shoppingCartRepository.findShoppingCartByUser(user).orElseThrow(ShoppingCartException::new);
    }

    public ShoppingCart getCartById(Long id) {
        return shoppingCartRepository.findById(id).orElseThrow(ShoppingCartException::new);
    }


}