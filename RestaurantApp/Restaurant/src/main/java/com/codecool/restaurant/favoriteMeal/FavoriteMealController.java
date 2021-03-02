package com.codecool.restaurant.favoriteMeal;

import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FavoriteMealController {

    private final FavoriteMealService favoriteMealService;
    private final UserService userService;

    public FavoriteMealController(FavoriteMealService favoriteMealService, UserService userService) {
        this.favoriteMealService = favoriteMealService;
        this.userService = userService;
    }

    @PostMapping(path = "{username}/favorites")
    public User addMealToFavorites(@PathVariable("username") String username, @RequestBody FavoriteMealDto favoriteMealDto){
        favoriteMealService.addFavoriteMeal(favoriteMealDto, username);
        return userService.getUserByUsername(username);
    }

    @GetMapping(path = "{username}/favorites")
    public Set<FavoriteMeal> getFavorites(@PathVariable("username") String username) {
        return favoriteMealService.getAllFavoriteMeals(username);

    }

    @DeleteMapping(path = "{username}/favorites/delete/{idMeal}")
    public void deleteFavoriteByIdMeal(@PathVariable("username") String username, @PathVariable("idMeal") String idMeal) {
        favoriteMealService.deleteFavoriteMealByIdMeal(username, idMeal);
    }
}
