package com.example.model;

public class Users {

    private String firstName;
    private String lastName;
    private String email;
    private Integer isInstructor;

    public Users() {};

    public Users(String firstName, String lastName, String email, Integer isInstructor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isInstructor = isInstructor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsInstructor() {
        return isInstructor;
    }

    public void setIsInstructor(Integer isInstructor) {
        this.isInstructor = isInstructor;
    }
}
