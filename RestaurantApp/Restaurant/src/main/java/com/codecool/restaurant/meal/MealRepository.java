package com.codecool.restaurant.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("mealDB")
public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal findByName(String string);

}
