package com.codecool.restaurant.favoriteMeal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FavoriteMealRepository extends JpaRepository<FavoriteMeal, Long> {

    FavoriteMeal findFavoriteMealByIdMeal(String idMeal);
}
