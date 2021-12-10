package ru.otus.spring.domain;

public class ErrorMessage {
    private int number;
    private String errorText;

    public ErrorMessage(int number, String errorText) {
        this.number = number;
        this.errorText = errorText;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
}
