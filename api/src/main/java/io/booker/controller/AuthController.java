package io.booker.controller;

import io.booker.application.common.dtos.JwtTokenDto;
import io.booker.application.contracts.services.auth.AuthService;
import io.booker.application.contracts.services.user.UserService;
import io.booker.domain.business.models.User;
import io.booker.model.request.LoginRequset;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthService authService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody LoginRequset loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPasswrod()));
//        if (!authentication.isAuthenticated()) {
//            throw new UsernameNotFoundException("User doesn't exist.");
//        }

        User user = this.userService.verifyUser(loginRequest.getUsername(), loginRequest.getPassword());
        return this.authService.generateTokens(user);
    }

    @PostMapping("/pwd")
    public String hashPassword(@RequestBody LoginRequset loginRequset) {
        return this.userService.hashPassword(loginRequset.getUsername(), loginRequset.getPassword());
    }
}
