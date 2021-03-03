package com.codecool.restaurant.favoriteMeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteMealRepository extends JpaRepository<FavoriteMeal, Long> {

    FavoriteMeal findFavoriteMealByIdMeal(String idMeal);
}
