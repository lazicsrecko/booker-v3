package io.booker.controller;


import io.booker.application.common.dtos.UserDto;
import io.booker.application.contracts.services.user.UserService;
import io.booker.domain.business.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userService.findAll();
    }

    @GetMapping("/user/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return this.userService.getUserById(userId);
    }

}
