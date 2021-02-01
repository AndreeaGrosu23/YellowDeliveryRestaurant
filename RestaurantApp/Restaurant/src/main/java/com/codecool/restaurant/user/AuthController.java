package com.codecool.restaurant.user;

import com.codecool.restaurant.security.JwtTokenServices;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Api(tags = "Authentication", value = "Authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenServices jwtTokenServices;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
    }

    @PostMapping(path = "/login")
    @ResponseBody
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserCredentials data, HttpServletResponse response){
        try {
            String username = data.getUsername();
            // authenticationManager.authenticate calls loadUserByUsername in CustomUserDetailsService
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenServices.createToken(username, roles);

            // create a cookie
            Cookie cookie = new Cookie("token",token);

            // expires in 7 days
            cookie.setMaxAge(7 * 24 * 60 * 60);

            // optional properties
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            response.addCookie(cookie);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("roles", roles);
            model.put("token", token);

            return new ResponseEntity<>(model,HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Rewrite a cookie
        Cookie cookie = new Cookie("token","NO_TOKEN");
        // expires in 7 days
        cookie.setMaxAge(7 * 24 * 60 * 60);
        // optional properties
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // add cookie to response
        response.addCookie(cookie);
        // return response entity
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
