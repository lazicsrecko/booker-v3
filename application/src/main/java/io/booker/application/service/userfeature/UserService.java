package io.booker.application.service.userfeature;

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
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public String hashPassword(String username, String password) {
        return this.passwordEncoder.encode(username.concat(password));
    }

    private Boolean checkPassword(String username, String userPassword, String providedPassword) {
        return this.passwordEncoder.matches(username.concat(providedPassword), userPassword);
    }
}
