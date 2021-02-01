package com.codecool.restaurant.favoriteMeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteMealRepository extends JpaRepository<FavoriteMeal, Long> {

    List<FavoriteMeal> getAllFavoriteMealsByUserId(Long user_id);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE from FavoriteMeal fm where fm.idMeal = :idMeal")
    void deleteFavoriteMealByIdMeal(String idMeal);

}
