package io.booker.infrastructure.mapper;

import io.booker.application.common.dtos.UserDto;
import io.booker.domain.business.models.User;
import io.booker.infrastructure.entity.UserEntity;

public class UserMapper {
    public static User toUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getVerified(),
                userEntity.getUserType(),
                RoleMapper.toRole(userEntity.getRole()),
                TenantMapper.toTenant(userEntity.getTenant())
        );
    }

    public static UserEntity toUserEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.isVerified(),
                user.getUserType()
        );
    }

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getUserType()
        );
    }
}
