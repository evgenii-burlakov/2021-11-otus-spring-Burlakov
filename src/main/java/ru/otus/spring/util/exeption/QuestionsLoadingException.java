package ru.otus.spring.util.exeption;

public class QuestionsLoadingException extends RuntimeException {
    public QuestionsLoadingException(String message) {
        super(message);
    }
}
