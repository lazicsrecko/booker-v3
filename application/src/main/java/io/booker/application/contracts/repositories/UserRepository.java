package io.booker.application.contracts.repositories;

import io.booker.domain.business.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findByUsername(String username);
}
