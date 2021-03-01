package com.codecool.restaurant.user;

import com.codecool.restaurant.shoppingCart.ShoppingCart;
import com.codecool.restaurant.shoppingCart.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Api(tags = "User Queries", value = "UserQueries")
public class UserController {

    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public UserController(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping
    @ApiOperation(value = "Add a new user")
    public void addUser(@Valid @RequestBody User user){
        userService.addUser(user);
        shoppingCartService.addCart(new ShoppingCart(user));
    }

    @GetMapping
    @ApiOperation(value = "Find all users registered in the DB")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path="{id}")
    @ApiOperation(value = "Find a user by id")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @DeleteMapping()
    @ApiOperation(value = "Delete a user from DB by ID")
    public void deletePersonByUsername(@CookieValue("token") String token){
        userService.deleteUserApp(token);
    }

    @GetMapping(path="view/{username}")
    @ApiOperation(value = "Find a user by username")
    public User getUserByUsername(@PathVariable("username") String username){
        return userService.getUserByUsername(username);
    }

    @PostMapping(path = "{username}/edit")
    @ApiOperation(value = "Update user details")
    public void updateUserProfile(@PathVariable("username") String username,@Valid @RequestBody UserDetailsDto updatedUser) {

        //throw exception if user not found
        userService.getUserByUsername(username);

        userService.updateUser(updatedUser, username);

    }
}

