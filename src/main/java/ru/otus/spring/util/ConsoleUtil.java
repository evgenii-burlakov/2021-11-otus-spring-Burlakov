package ru.otus.spring.util;

import java.util.Scanner;

public final class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static void print(String sting) {
        System.out.println(sting);
    }

    public static String readNonEmptyString() {
        String result;
        while (true) {
            result = scanner.nextLine();
            if (result.isBlank()) {
                print("Empty response is not allowed");
            } else {
                return result;
            }
        }
    }

    public static String readAnswerFromOptions(int numberOfOptions) {
        String result;
        while (true) {
            result = scanner.nextLine();
            int intResult;
            try {
                intResult = Integer.parseInt(result);
                if (intResult > 0 && intResult <= numberOfOptions) {
                    return result;
                } else {
                    print(String.format("Please enter a number from 1 to %d", numberOfOptions));
                }
            } catch (NumberFormatException e) {
                print(String.format("Please enter a number from 1 to %d", numberOfOptions));
            }
        }
    }

    public static void closeScanner() {
        scanner.close();
    }
}
