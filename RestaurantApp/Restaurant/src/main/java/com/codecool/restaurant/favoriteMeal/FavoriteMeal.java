package com.codecool.restaurant.favoriteMeal;

import com.codecool.restaurant.user.User;
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

    // needs to be refactored to many to many relationship
    // (one user can have many favorites, one meal can be fave to many users)
    @OneToOne
    @JoinColumn(name="userApp_id", referencedColumnName = "id")
    private User user;

    private String name;
    private String image;

    public FavoriteMeal(User user, String name, String image, String idMeal) {
        this.user = user;
        this.name = name;
        this.image = image;
        this.idMeal = idMeal;
    }
}

