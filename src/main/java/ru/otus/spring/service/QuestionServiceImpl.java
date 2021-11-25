package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean validateAnswer(Question question, String answer) {
        Integer answerOptionsSize = question.getAnswerOptions() == null? null: question.getAnswerOptions().size();
        if (answerOptionsSize != null && answerOptionsSize != 0) {
            int answerOption;
            try {
                answerOption = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.printf("Please enter a number from 1 to %d\n", answerOptionsSize);
                return false;
            }
            if (answerOption > answerOptionsSize || answerOption < 1) {
                System.out.printf("Please enter a number from 1 to %d\n", answerOptionsSize);
                return false;
            }
        } else {
            if (answer.isBlank()) {
                System.out.print("An empty response field is not allowed\n");
                return false;
            }
        }

        return true;
    }
}
