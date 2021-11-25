package ru.otus.spring.util.exeption;

public class ApplicationError extends RuntimeException {
    public ApplicationError(String message) {
        super(message);
    }
}
