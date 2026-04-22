package io.booker.domain.business.models;

public class Tenant {
    private Long id;
    private String name;

    public Tenant(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
