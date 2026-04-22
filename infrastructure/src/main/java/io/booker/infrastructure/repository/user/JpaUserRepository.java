package io.booker.infrastructure.repository.user;

import io.booker.domain.business.models.User;
import io.booker.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
