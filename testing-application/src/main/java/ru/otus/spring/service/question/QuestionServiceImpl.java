package ru.otus.spring.service.question;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.question.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;

    @Override
    public List<Question> getAll() {
        return dao.getAll();
    }

    @Override
    public List<String> getAllQuestionsTexts() {
        return dao.getAll().stream()
                .map(Question::getQuestion)
                .collect(Collectors.toList());
    }
}
