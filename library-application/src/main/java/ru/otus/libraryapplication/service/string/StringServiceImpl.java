package ru.otus.libraryapplication.service.string;

import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.stream.Stream;

@Service
public class StringServiceImpl implements StringService {

    @Override
    public String beautifyStringName(String name) {
        return Stream.of(name)
                .map(String::strip)
                .map(s -> s.replaceAll("\\s{2,}", " "))
                .map(s -> s.toUpperCase(Locale.ROOT))
                .findFirst().orElse(null);
    }
}
