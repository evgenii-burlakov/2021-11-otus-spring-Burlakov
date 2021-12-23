package ru.otus.spring.service.locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.question.QuestionService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class LocaleServiceImpl implements LocaleService {
    private final String locale;
    private final MessageSource messageSource;
    private final QuestionService questionService;

    public LocaleServiceImpl(QuestionService questionService, MessageSource messageSource, @Value("${application.language}") String locale) {
        this.questionService = questionService;
        this.locale = locale;
        this.messageSource = messageSource;
    }

    @Override
    public List<Question> getLocalizedQuestions() {
        return questionService.getAll().stream()
                .filter(q -> q.getLocale().equals(locale))
                .collect(Collectors.toList());
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
