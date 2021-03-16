package com.codecool.restaurant.favoriteMeal;

import com.codecool.restaurant.exception.NoDataFoundException;
import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@AllArgsConstructor
@Service
public class FavoriteMealService {

    private final FavoriteMealRepository favoriteMealRepository;
    private final UserService userService;

    @Transactional
    public void addFavoriteMeal(FavoriteMealDto favoriteMealDto, String username) {
        User user = userService.getUserByUsername(username);

        if(favoriteMealRepository.findFavoriteMealByIdMeal(favoriteMealDto.getIdMeal()).isEmpty()) {
            FavoriteMeal favoriteMeal = new FavoriteMeal(favoriteMealDto.getName(), favoriteMealDto.getImage(), favoriteMealDto.getIdMeal());
            favoriteMealRepository.save(favoriteMeal);
            user.getFavoriteMeals().add(favoriteMeal);
        } else {
            FavoriteMeal favoriteMeal= favoriteMealRepository.findFavoriteMealByIdMeal(favoriteMealDto.getIdMeal()).orElseThrow(NoDataFoundException::new);
            user.getFavoriteMeals().add(favoriteMeal);
        }

        userService.updateUser(user);
    }

    public Set<FavoriteMeal> getAllFavoriteMeals(String username) {
        User user = userService.getUserByUsername(username);
        //if favoriteMeals is an empty set we want it returned like that, instead of having an exception
        return user.getFavoriteMeals();
    }

    @Transactional
    public void deleteFavoriteMealByIdMeal(String username, String idMeal) {
        User user = userService.getUserByUsername(username);
        //if there is no favoriteMeal with that id an exception will be thrown
        FavoriteMeal favoriteMeal = favoriteMealRepository.findFavoriteMealByIdMeal(idMeal).orElseThrow(NoDataFoundException::new);
        user.getFavoriteMeals().remove(favoriteMeal);
        userService.updateUser(user);
    }

}
