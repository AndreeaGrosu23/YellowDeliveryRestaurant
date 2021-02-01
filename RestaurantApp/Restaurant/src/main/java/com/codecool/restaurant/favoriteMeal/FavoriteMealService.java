package com.codecool.restaurant.favoriteMeal;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FavoriteMealService {

    private final FavoriteMealRepository favoriteMealRepository;

    public FavoriteMealService(FavoriteMealRepository favoriteMealRepository){
        this.favoriteMealRepository = favoriteMealRepository;
    }

    public void addFavoriteMeal(FavoriteMeal favoriteMeal) {
        favoriteMealRepository.save(favoriteMeal);
    }

    public List<FavoriteMeal> getAllFavoriteMeals(Long user_id) {
        return favoriteMealRepository.getAllFavoriteMealsByUserId(user_id);
    }

    @Transactional
    public void deleteFavoriteMealByIdMeal(String idMeal) {
        favoriteMealRepository.deleteFavoriteMealByIdMeal(idMeal);
    }
}
