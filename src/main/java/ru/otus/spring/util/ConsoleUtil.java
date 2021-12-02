package ru.otus.spring.util;

import java.util.Scanner;

public final class ConsoleUtil {
    public static void print(String sting) {
        System.out.println(sting);
    }

    public static String readNonEmptyString() {
        Scanner scanner = new Scanner(System.in);
        String result = "";
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
        Scanner scanner = new Scanner(System.in);
        String result;
        while (true) {
            result = scanner.nextLine();
            int intResult = 0;
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
}
