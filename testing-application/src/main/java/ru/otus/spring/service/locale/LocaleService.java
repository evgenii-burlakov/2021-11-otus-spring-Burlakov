package ru.otus.spring.service.locale;

public interface LocaleService {
    String getMessage(String message);

    String getMessage(String message, String... args);
}
