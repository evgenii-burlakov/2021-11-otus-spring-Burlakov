package ru.otus.spring.service.question;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.question.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> getAll() {
        return dao.getAll();
    }
}
