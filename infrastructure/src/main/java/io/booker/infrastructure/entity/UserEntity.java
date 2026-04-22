package io.booker.infrastructure.entity;

import io.booker.domain.business.models.User;
import io.booker.domain.enums.UserType;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "is_verified")
    private Boolean isVerified;
    @Column(name = "tenant_id")
    private Long tenantId;
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "user_type")
    private UserType userType;

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

    public User mapToUser() {
        return new User(
                this.id,
                this.firstName,
                this.lastName,
                this.username,
                this.password,
                this.email,
                this.isVerified,
                this.tenantId,
                this.roleId,
                this.userType);
    }
}
