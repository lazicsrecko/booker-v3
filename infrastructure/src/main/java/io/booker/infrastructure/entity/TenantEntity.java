package io.booker.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tenants")
public class TenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
