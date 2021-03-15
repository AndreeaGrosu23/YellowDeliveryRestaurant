package com.codecool.restaurant;

import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public void run(String... args) {

        User user = new User("John", "Doe", "admin", "j.doe@gmail.com", "", "", passwordEncoder.encode("admin"));
        userRepository.save(user);
    }
}
