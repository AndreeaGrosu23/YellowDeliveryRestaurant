package com.codecool.restaurant.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Api(tags = "User Queries", value = "UserQueries")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ApiOperation(value = "Add a new user")
    public void addUser(@Valid @RequestBody User user){
        userService.addUser(user);
    }

    @GetMapping(path="/all-users")
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
    @ApiOperation(value = "Delete a user from DB")
    public void deletePersonByUsername(@CookieValue("token") String token){
        userService.deleteUserApp(token);
    }

    @GetMapping
    @ApiOperation(value = "Find a user by username")
    public User getUserByUsername(Authentication authentication){
        String username = authentication.getName();
        return userService.getUserByUsername(username);
    }

    @PutMapping
    @ApiOperation(value = "Update user details")
    public void updateUserProfile(@Valid @RequestBody UserDetailsDto updatedUser, Authentication authentication) {
        String username = authentication.getName();
        userService.updateUser(updatedUser, username);
    }
}

