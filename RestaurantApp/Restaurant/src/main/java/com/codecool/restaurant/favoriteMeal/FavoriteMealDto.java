package com.codecool.restaurant.favoriteMeal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteMealDto {
    private String idMeal;
    private String name;
    private String image;
}
