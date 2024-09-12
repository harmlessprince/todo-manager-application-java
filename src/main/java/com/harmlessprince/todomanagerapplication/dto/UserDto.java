package com.harmlessprince.todomanagerapplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * A DTO for the {@link com.harmlessprince.todomanagerapplication.models.User} entity
 */


public class UserDto {
    private Integer id;
    @NotBlank(message = "first name is compulsory") private String firstName;
    @NotBlank(message = "middle name is compulsory") private String middleName;
    @NotBlank(message = "last name is compulsory") private String lastName;
    @Size(min = 3, message = "username should be at least 3 characters") @NotBlank(message = "username is compulsory") private String username;
    @Email(message = "Please provide a valid email address") @NotBlank(message = "Email is compulsory") private String email;
    @Size(min = 10, message = "phone number must be at least 10 characters") @NotBlank(message = "phone number is compulsory") private String phoneNumber;
    @NotBlank(message = "password is compulsory") private String password;




    public UserDto(String firstName, String middleName, String lastName, String username, String email, String phoneNumber, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public UserDto() {
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.middleName, entity.middleName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.username, entity.username) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.phoneNumber, entity.phoneNumber) &&
                Objects.equals(this.password, entity.password);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "firstName = " + firstName + ", " +
                "middleName = " + middleName + ", " +
                "lastName = " + lastName + ", " +
                "username = " + username + ", " +
                "email = " + email + ", " +
                "phoneNumber = " + phoneNumber + ", " +
                "password = " + password + ")";
    }


}