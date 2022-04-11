package ru.otus.integration.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private final String name;
    private List<Student> friends = new ArrayList<>();
    private boolean readyToBar;

    public boolean isReadyToBar() {
        return readyToBar;
    }

    public void setReadyToBar(boolean readyToBar) {
        this.readyToBar = readyToBar;
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, List<Student> friends) {
        this.name = name;
        this.friends = friends;
    }

    public String getName() {
        return name;
    }

    public List<Student> getFriends() {
        return friends;
    }
}
