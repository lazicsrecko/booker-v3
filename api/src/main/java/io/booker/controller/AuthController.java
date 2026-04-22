package io.booker.controller;

import io.booker.application.common.dtos.JwtTokenDto;
import io.booker.application.common.model.requests.LoginRequest;
import io.booker.application.contracts.services.auth.AuthService;
import io.booker.application.contracts.services.user.UserService;
import io.booker.domain.business.models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody LoginRequest loginRequest) {
        User user = this.userService.verifyUser(loginRequest.getUsername(), loginRequest.getPassword());
        return this.authService.generateTokens(user);
    }
}
