package ru.otus.spring.service.string;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionType;
import ru.otus.spring.service.locale.LocaleService;

@Service
@AllArgsConstructor
public class StringServiceImpl implements StringService {
    private final LocaleService localeService;

    @Override
    public String toStringQuestion(Question question) {
        String stringQuestion = question.getQuestion();
        int number = question.getNumber();

        StringBuilder sb = new StringBuilder(localeService.getMessage("strings.question", String.valueOf(number))).append("\n");
        sb.append(stringQuestion).append("\n");
        if (question.getQuestionType().equals(QuestionType.ANSWER_OPTIONS)) {
            sb.append(localeService.getMessage("strings.answerOptions")).append("\n");
            for (int i = 0; i < question.getQuestionAnswers().size(); i++) {
                sb.append(String.format("%d) %s \n", i + 1, question.getQuestionAnswers().get(i).getQuestionAnswer()));
            }
        }

        return sb.toString();
    }
}
