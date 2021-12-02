package ru.otus.spring.domain;

public class User {
    private String firstName;
    private String secondNuestion;
    private String surname;

    public User(String firstName, String secondNuestion, String surname) {
        this.firstName = firstName;
        this.secondNuestion = secondNuestion;
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondNuestion() {
        return secondNuestion;
    }

    public void setSecondNuestion(String secondNuestion) {
        this.secondNuestion = secondNuestion;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
