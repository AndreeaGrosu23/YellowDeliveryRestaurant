package com.codecool.restaurant.favoriteMeal;

import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserRepository;
import com.codecool.restaurant.user.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class FavoriteMealService {

    private final FavoriteMealRepository favoriteMealRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public FavoriteMealService(FavoriteMealRepository favoriteMealRepository, UserService userService, UserRepository userRepository) {
        this.favoriteMealRepository = favoriteMealRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addFavoriteMeal(FavoriteMealDto favoriteMealDto, String username) {
        User user = userService.getUserByUsername(username);

        if(favoriteMealRepository.findFavoriteMealByIdMeal(favoriteMealDto.getIdMeal())==null) {
            FavoriteMeal favoriteMeal = new FavoriteMeal(favoriteMealDto.getName(), favoriteMealDto.getImage(), favoriteMealDto.getIdMeal());
            favoriteMealRepository.save(favoriteMeal);
            user.getFavoriteMeals().add(favoriteMeal);
        } else {
            FavoriteMeal favoriteMeal= favoriteMealRepository.findFavoriteMealByIdMeal(favoriteMealDto.getIdMeal());
            user.getFavoriteMeals().add(favoriteMeal);
        }

        userRepository.save(user);
    }

    public Set<FavoriteMeal> getAllFavoriteMeals(String username) {
        User user = userService.getUserByUsername(username);
        return user.getFavoriteMeals();
    }

    @Transactional
    public void deleteFavoriteMealByIdMeal(String username, String idMeal) {
        User user = userService.getUserByUsername(username);
        FavoriteMeal favoriteMeal = favoriteMealRepository.findFavoriteMealByIdMeal(idMeal);
        user.getFavoriteMeals().remove(favoriteMeal);
        userRepository.save(user);
    }
}
