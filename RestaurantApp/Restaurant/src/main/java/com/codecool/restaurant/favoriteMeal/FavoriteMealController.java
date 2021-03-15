package com.codecool.restaurant.favoriteMeal;

import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("api/v1/user/favorites")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FavoriteMealController {

    private final FavoriteMealService favoriteMealService;
    private final UserService userService;

    public FavoriteMealController(FavoriteMealService favoriteMealService, UserService userService) {
        this.favoriteMealService = favoriteMealService;
        this.userService = userService;
    }

    @PostMapping()
    public User addMealToFavorites(@RequestBody FavoriteMealDto favoriteMealDto, Authentication authentication){
        String username = authentication.getName();
        favoriteMealService.addFavoriteMeal(favoriteMealDto, username);
        return userService.getUserByUsername(username);
    }

    @GetMapping()
    public Set<FavoriteMeal> getFavorites(Authentication authentication) {
        String username = authentication.getName();
        return favoriteMealService.getAllFavoriteMeals(username);

    }

    @DeleteMapping(path = "/{idMeal}")
    public void deleteFavoriteByIdMeal(@PathVariable("idMeal") String idMeal, Authentication authentication) {
        String username = authentication.getName();
        favoriteMealService.deleteFavoriteMealByIdMeal(username, idMeal);
    }
}
