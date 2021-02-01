package com.codecool.restaurant.meal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {
    private final MealRepository mealRepository;

    public MealService(@Qualifier("mealDB") MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public void addMeal(Meal meal){
        mealRepository.save(meal);
    }

    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    public Meal findByName(String name){
        return mealRepository.findByName(name);
    }


}
