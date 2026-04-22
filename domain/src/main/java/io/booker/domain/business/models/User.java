package io.booker.domain.business.models;

import io.booker.domain.enums.UserType;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Boolean isVerified;
    private Long tenantId;
    private Long roleId;
    private UserType userType;

    public User(Long id, String firstName, String lastName, String username, String password, String email, Boolean isVerified, Long tenantId, Long roleId, UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isVerified = isVerified;
        this.tenantId = tenantId;
        this.roleId = roleId;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public UserType getUserType() {
        return userType;
    }
}
