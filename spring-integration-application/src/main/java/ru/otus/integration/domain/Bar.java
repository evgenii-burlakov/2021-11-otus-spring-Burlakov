package ru.otus.integration.domain;

public class Bar {
    private final String name;
    private boolean isFree= false;


    public Bar(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
