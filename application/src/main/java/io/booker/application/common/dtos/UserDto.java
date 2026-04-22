package io.booker.application.common.dtos;

import io.booker.domain.business.models.User;
import io.booker.domain.enums.UserType;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private UserType userType;

    public UserDto(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getUserType());
    }

    public UserDto(Long id, String firstName, String lastName, String username, String email, UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }
}
