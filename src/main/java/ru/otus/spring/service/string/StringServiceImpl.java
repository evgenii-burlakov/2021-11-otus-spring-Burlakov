package ru.otus.spring.service.string;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionType;

import java.util.Locale;

@Service
public class StringServiceImpl implements StringService {
    private final MessageSource messageSource;
    private final String locale;

    public StringServiceImpl(MessageSource messageSource, @Value("${application.language}") String locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String toStringQuestion(Question question) {
        String stringQuestion = question.getQuestion();
        int number = question.getNumber();

        StringBuilder sb = new StringBuilder(getMessage("strings.question", String.valueOf(number))).append("\n");
        sb.append(stringQuestion).append("\n");
        if (question.getQuestionType().equals(QuestionType.ANSWER_OPTIONS)) {
            sb.append(getMessage("strings.answerOptions")).append("\n");
            for (int i = 0; i < question.getQuestionAnswers().size(); i++) {
                sb.append(String.format("%d) %s \n", i + 1, question.getQuestionAnswers().get(i).getQuestionAnswer()));
            }
        }

        return sb.toString();
    }

    @Override
    public String getMessage(String message) {
        return messageSource.getMessage(
                message,
                null,
                Locale.forLanguageTag(locale));
    }

    @Override
    public String getMessage(String message, String... args) {
        return messageSource.getMessage(
                message,
                args,
                Locale.forLanguageTag(locale));
    }
}
