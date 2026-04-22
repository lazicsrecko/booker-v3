package io.booker.infrastructure.entity;

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
    @Column(name = "user_type")
    private UserType userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    public UserEntity() {
    }

    public UserEntity(Long id, String firstName, String lastName, String username, String password, String email, Boolean isVerified, UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isVerified = isVerified;
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

    public UserType getUserType() {
        return userType;
    }

    public TenantEntity getTenant() {
        return tenant;
    }

    public RoleEntity getRole() {
        return role;
    }
}
