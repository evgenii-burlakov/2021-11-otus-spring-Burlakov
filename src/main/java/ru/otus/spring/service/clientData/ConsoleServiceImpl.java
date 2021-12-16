package ru.otus.spring.service.clientData;

import ru.otus.spring.service.string.StringService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleServiceImpl implements ClientDataService {
    private final PrintStream printStream;
    private final InputStream inputStream;
    private final StringService stringService;

    public ConsoleServiceImpl(PrintStream printStream, InputStream inputStream, StringService stringService) {
        this.printStream = printStream;
        this.inputStream = inputStream;
        this.stringService = stringService;
    }

    @Override
    public String getNotEmptyString() {
        Scanner scanner = new Scanner(inputStream);
        String result;
        while (true) {
            result = scanner.nextLine();
            if (result.isBlank()) {
                printString(stringService.getMessage("strings.notEmptyStringError"));
            } else {
                return result;
            }
        }
    }

    @Override
    public void printString(String string) {
        printStream.println(string);
    }
}
