package com.codecool.restaurant.favoriteMeal;

import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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

    // to refactor to favorite DTO
    @PutMapping(path = "{username}/favorites/{idMeal}")
    public void addMealToFavorites(@PathVariable("username") String username, @RequestBody HashMap<String, String> listMealAttributes){
        User user = userService.getUserByUsername(username).orElse(null);
        String idMeal = listMealAttributes.get("idMeal");
        String name = listMealAttributes.get("strMeal");
        String image = listMealAttributes.get("strMealThumb");
        FavoriteMeal favoriteMeal = new FavoriteMeal(user,name,image,idMeal);
        favoriteMealService.addFavoriteMeal(favoriteMeal);
    }

    @GetMapping(path = "{username}/favorites")
    public List<FavoriteMeal> getFavorites(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username).orElse(null);
        return favoriteMealService.getAllFavoriteMeals(user.getId());
    }

    @DeleteMapping(path = "{username}/favorites/delete/{idMeal}")
    public void deleteFavoriteByIdMeal(@PathVariable("username") String username, @PathVariable("idMeal") String idMeal) {
        User user = userService.getUserByUsername(username).orElse(null);
        List<FavoriteMeal> favoriteMeals = favoriteMealService.getAllFavoriteMeals(user.getId());
        for (FavoriteMeal favoriteMeal : favoriteMeals) {
            if (favoriteMeal.getIdMeal().equals(idMeal)) {
                favoriteMealService.deleteFavoriteMealByIdMeal(idMeal);
            }
        }
    }
}
