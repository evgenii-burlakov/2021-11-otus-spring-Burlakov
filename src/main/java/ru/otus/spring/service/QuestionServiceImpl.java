package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionService;
import ru.otus.spring.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionService dao;

    public QuestionServiceImpl(QuestionService dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> getAll() {
        return dao.getAll();
    }
}
