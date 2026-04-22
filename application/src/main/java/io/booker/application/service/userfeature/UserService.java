package io.booker.application.service.userfeature;

import io.booker.application.common.dtos.UserDto;
import io.booker.application.contracts.repositories.UserRepository;
import io.booker.domain.business.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements io.booker.application.contracts.services.user.UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User verifyUser(String username, String password) {
        try {
            User user = this.userRepository.findByUsername(username);

            if (user == null) {
                throw new Exception(String.format("User with username \"%s\" not found", username));
            }

            Boolean isAuthenticated = checkPassword(user.getUsername(), user.getPassword(), password);

            if (!isAuthenticated) {
                throw new Exception("Wrong username and password combination.");
            }

            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = this.userRepository.getUserById(userId);
        return new UserDto(user);
    }

    private Boolean checkPassword(String username, String userPassword, String providedPassword) {
        return this.passwordEncoder.matches(username.concat(providedPassword), userPassword);
    }
}
