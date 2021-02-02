package com.codecool.restaurant.user;

import com.codecool.restaurant.exception.NoDataFoundException;
import com.codecool.restaurant.security.JwtTokenServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Add encoder for password
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtTokenServices jwtTokenServices;

    public UserService(UserRepository userRepository, JwtTokenServices jwtTokenServices){
        this.userRepository = userRepository;
        this.jwtTokenServices = jwtTokenServices;
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Persisting User with encoded password
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public List<User> getAllUsers() {
        List<User> listUsers = userRepository.findAll();
        if (listUsers.isEmpty()) {
            throw new NoDataFoundException();
        }
        return listUsers;
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(NoDataFoundException::new);
    }

    @Transactional
    public void updateUser(String firstName, String lastName, String emailAddress, String password,String deliveryAddress, String phoneNumber, String userName) {
        userRepository.updateUser(firstName, lastName, emailAddress, password, deliveryAddress, phoneNumber, userName);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUserName(username).orElseThrow(NoDataFoundException::new);
    }

    public void deleteUserApp(String token){
        Authentication userName = jwtTokenServices.parseUserFromTokenInfo(token);
        User user = getUserByUsername(userName.getName());
        userRepository.deleteById(user.getId());
    }
}