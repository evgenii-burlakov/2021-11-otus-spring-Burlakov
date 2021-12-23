package ru.otus.spring.service.clientData;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.service.string.StringService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@RequiredArgsConstructor
public class ConsoleServiceImpl implements ClientDataService {
    private final PrintStream printStream;
    private final InputStream inputStream;
    private final StringService stringService;

    @Override
    public String getNotEmptyString(String message) {
        Scanner scanner = new Scanner(inputStream);
        String result;
        while (true) {
            result = scanner.nextLine();
            if (result.isBlank()) {
                printString(message);
            } else {
                return result;
            }
        }
    }

    @Override
    public void printString(String string) {
        printStream.println(string);
    }

    //stringService.getMessage("strings.notEmptyStringError")
}
