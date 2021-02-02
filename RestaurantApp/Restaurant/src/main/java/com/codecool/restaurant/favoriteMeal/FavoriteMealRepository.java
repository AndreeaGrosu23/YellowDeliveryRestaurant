package com.codecool.restaurant.favoriteMeal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteMealRepository extends JpaRepository<FavoriteMeal, Long> {

    List<FavoriteMeal> getAllFavoriteMealsByUserId(Long user_id);

    Optional<FavoriteMeal> findFavoriteMealByIdMeal(String idMeal);

}
