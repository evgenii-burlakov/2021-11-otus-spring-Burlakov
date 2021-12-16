package ru.otus.spring.service.question;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.question.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;

    @Override
    public List<Question> getAll() {
        return dao.getAll();
    }
}
