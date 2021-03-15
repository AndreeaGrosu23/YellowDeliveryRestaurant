package com.codecool.restaurant.user;

import com.codecool.restaurant.favoriteMeal.FavoriteMeal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@ApiModel(description = "Model to create a new User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Unique user id")
    private Long id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Column(unique=true)
    @NotBlank(message = "Username is mandatory")
    @ApiModelProperty(notes = "Unique and mandatory username", example = "j.doe")
    private String userName;

    @Column(unique = true)
    @NotBlank(message = "E-mail is mandatory")
    @Email(message = "E-mail must be valid")
    @ApiModelProperty(notes = "Unique and mandatory email", example = "j.doe@gmail.com")
    private String emailAddress;

    private String deliveryAddress;

    private String phoneNumber;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 4, message = "Password must be minimum 4 characters")
    private String password;

    // roles of the user (ADMIN, USER,..)
    private String userRole= "ROLE_USER";

    @ManyToMany(cascade = { CascadeType.ALL })
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "FAVORITE_USERS",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "favoriteMeal_id") }
    )
    Set<FavoriteMeal> favoriteMeals = new HashSet<>();

    public User(String firstName, String lastName, String userName, String emailAddress, String deliveryAddress, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }


}
