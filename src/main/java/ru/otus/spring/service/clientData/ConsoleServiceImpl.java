package ru.otus.spring.service.clientData;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionType;

import java.util.Scanner;

@Service
public class ConsoleServiceImpl implements ClientDataService {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getString() {
        String result;
        while (true) {
            result = scanner.nextLine();
            if (result.isBlank()) {
                printString("Empty response is not allowed");
            } else {
                return result;
            }
        }
    }

    @Override
    public void printString(String string) {
        System.out.println(string);
    }

    @Override
    public String toStringQuestion(Question question) {
        String stringQuestion = question.getQuestion();
        int number = question.getNumber();

        StringBuilder sb = new StringBuilder(String.format("Question №%d:\n%s\n", number, stringQuestion));
        if (question.getQuestionType().equals(QuestionType.ANSWER_OPTIONS)) {
            sb.append("Choose from the proposed answer options (enter the number): \n");
            for (int i = 0; i < question.getQuestionAnswers().size(); i++) {
                sb.append(String.format("%d) %s \n", i + 1, question.getQuestionAnswers().get(i).getQuestionAnswer()));
            }
        }

        return sb.toString();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
