package com.codecool.restaurant.favoriteMeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteMealRepository extends JpaRepository<FavoriteMeal, Long> {

    Optional<FavoriteMeal> findFavoriteMealByIdMeal(String idMeal);
}
