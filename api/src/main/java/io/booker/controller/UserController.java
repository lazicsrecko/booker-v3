package io.booker.controller;


import io.booker.application.contracts.services.user.UserService;
import io.booker.domain.business.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
//    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public UserController(/* JwtService jwtService,*/ AuthenticationManager authManager, UserService userService) {
//        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return this.userService.getUserByUsername(username);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userService.findAll();
    }
//
//    @GetMapping("/user/{userId}")
//    public User getUserById(@PathVariable(name = "userId") Long userId) {
//        return this.userRepository.getUserById(userId);
//    }

}
