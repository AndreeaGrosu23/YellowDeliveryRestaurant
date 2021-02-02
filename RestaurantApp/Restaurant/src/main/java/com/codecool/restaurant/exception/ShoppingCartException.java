package com.codecool.restaurant.exception;

public class ShoppingCartException extends RuntimeException {

    public ShoppingCartException() {
        super("No shopping cart found!");
    }
}
