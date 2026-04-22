package io.booker.infrastructure.mapper;

import io.booker.domain.business.models.Tenant;
import io.booker.infrastructure.entity.TenantEntity;

public class TenantMapper {
    public static Tenant toTenant(TenantEntity tenantEntity) {
        return new Tenant(tenantEntity.getId(), tenantEntity.getName());
    }
}
