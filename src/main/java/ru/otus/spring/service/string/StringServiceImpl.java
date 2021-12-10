package ru.otus.spring.service.string;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionType;

@Service
public class StringServiceImpl implements StringService{
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
