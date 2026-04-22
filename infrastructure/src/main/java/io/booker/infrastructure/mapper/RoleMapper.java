package io.booker.infrastructure.mapper;

import io.booker.domain.business.models.Role;
import io.booker.infrastructure.entity.RoleEntity;

public class RoleMapper {
    public static Role toRole(RoleEntity roleEntity) {
        return new Role(roleEntity.getId(), roleEntity.getName());
    }
}
