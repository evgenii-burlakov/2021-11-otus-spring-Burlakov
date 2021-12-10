package ru.otus.spring.service.clientData;

import ru.otus.spring.service.errorMessage.ErrorMessageService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleServiceImpl implements ClientDataService {
    private final PrintStream printStream;
    private final InputStream inputStream;
    private final ErrorMessageService errorMessageService;

    public ConsoleServiceImpl(PrintStream printStream, InputStream inputStream, ErrorMessageService errorMessageService) {
        this.printStream = printStream;
        this.inputStream = inputStream;
        this.errorMessageService = errorMessageService;
    }

    @Override
    public String getString() {
        Scanner scanner = new Scanner(inputStream);
        String result;
        while (true) {
            result = scanner.nextLine();
            if (result.isBlank()) {
                printString(errorMessageService.getErrorText(1));
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
