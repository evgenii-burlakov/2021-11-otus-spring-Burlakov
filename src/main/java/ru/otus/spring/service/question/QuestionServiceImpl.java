package ru.otus.spring.service.question;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswers;
import ru.otus.spring.domain.QuestionType;

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

    @Override
    public QuestionAnswers getRightAnswer(Question question) {
        if (question.getQuestionType().equals(QuestionType.FREE_ANSWER)) {
            return question.getQuestionAnswers().get(0);
        } else if (question.getQuestionType().equals(QuestionType.ANSWER_OPTIONS)) {
            return question.getQuestionAnswers().stream()
                    .filter(QuestionAnswers::isRightAnswer)
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public String toStringQuestion(Question question) {
        String stringQuestion = question.getQuestion();
        int number = question.getNumber();

        StringBuilder sb = new StringBuilder(String.format("Question №%d:\n%s\n", number, stringQuestion));
        if (question.getQuestionType().equals(QuestionType.ANSWER_OPTIONS)) {
            sb.append("Choose from the proposed answer options (enter the number): \n");
            for (int i = 0; i < question.getQuestionAnswers().size(); i++) {
                sb.append(String.format("%d) %s \n", i + 1, question.getQuestionAnswers().get(i).getQuestionAnswer()));
            }
        }

        return sb.toString();
    }
}
