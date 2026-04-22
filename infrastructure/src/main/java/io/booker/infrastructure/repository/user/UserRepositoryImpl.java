package io.booker.infrastructure.repository.user;

import io.booker.application.contracts.repositories.UserRepository;
import io.booker.domain.business.models.User;
import io.booker.infrastructure.entity.UserEntity;
import io.booker.infrastructure.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public List<User> findAll() {
        return this.jpaUserRepository.findAll()
                .stream()
                .map(userEntity -> UserMapper.toUser(userEntity))
                .toList();
    }

    @Override
    public User findByUsername(String username) {
        UserEntity userEntity = this.jpaUserRepository.findByUsername(username);
        return UserMapper.toUser(userEntity);
    }

    @Override
    public User getUserById(Long userId) {
        UserEntity userEntity = this.jpaUserRepository.getReferenceById(userId);
        return UserMapper.toUser(userEntity);
    }
}
