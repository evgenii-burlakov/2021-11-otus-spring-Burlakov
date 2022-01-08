package ru.otus.spring.service.locale;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.locale.LocaleProvider;

@Service
@RequiredArgsConstructor
public class LocaleServiceImpl implements LocaleService {
    private final MessageSource messageSource;
    private final LocaleProvider localeProvider;

    @Override
    public String getMessage(String message) {
        return messageSource.getMessage(
                message,
                null,
                localeProvider.getLocale());
    }

    @Override
    public String getMessage(String message, String... args) {
        return messageSource.getMessage(
                message,
                args,
                localeProvider.getLocale());
    }
}
