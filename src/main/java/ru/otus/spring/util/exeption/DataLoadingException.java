package ru.otus.spring.util.exeption;

public class DataLoadingException extends RuntimeException {
    public DataLoadingException(String message) {
        super(message);
    }
}
