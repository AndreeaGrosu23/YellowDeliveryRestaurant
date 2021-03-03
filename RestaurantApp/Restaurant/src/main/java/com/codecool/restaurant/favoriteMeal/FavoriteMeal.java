package com.codecool.restaurant.favoriteMeal;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="FAVORITE_MEAL")
public class FavoriteMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String idMeal;
    private String name;
    private String image;

    public FavoriteMeal(String name, String image, String idMeal) {
        this.name = name;
        this.image = image;
        this.idMeal = idMeal;
    }

}

