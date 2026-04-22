package io.booker.model;

import jakarta.persistence.*;

//@Entity
//@Table(name = "users")
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Column(name = "id")
    private Long id;

//    @Column(name = "username")
    private String username;

    private String password;

    private String email;

//    @Column(name = "role_id")
    private Long roleId;

    private Boolean isDeleted;

    private Long tenantId;

    private String firstName;

    private String lastName;

    private Long userType;

    private Boolean isVerified;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getUserType() {
        return userType;
    }

    public Boolean getVerified() {
        return isVerified;
    }
}
