package ru.otus.spring.dao.errorMessage;

import ru.otus.spring.domain.ErrorMessage;
import ru.otus.spring.domain.Question;

import java.util.List;

public interface ErrorMessageDao {
    ErrorMessage get(int number);
}
