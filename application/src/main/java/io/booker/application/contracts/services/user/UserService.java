package io.booker.application.contracts.services.user;

import io.booker.application.common.dtos.UserDto;
import io.booker.domain.business.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User verifyUser(String username, String password);
    UserDto getUserById(Long userId);
}
