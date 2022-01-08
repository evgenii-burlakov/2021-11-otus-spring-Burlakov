package ru.otus.spring.dao.locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Locale;

@Repository
public class LocaleProviderImpl implements LocaleProvider {
    private final Locale locale;

    public LocaleProviderImpl(@Value("${application.language}") String language) {
        this.locale = Locale.forLanguageTag(language);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
