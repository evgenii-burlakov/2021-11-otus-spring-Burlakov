package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionService;
import ru.otus.spring.domain.Question;

import java.util.List;

public class ConsoleServiceImpl implements ConsoleService{
    private final QuestionService service;

    public ConsoleServiceImpl(QuestionService service) {
        this.service = service;
    }

    @Override
    public void printAllQuestions() {
        System.out.println();
        for (Question question : service.getAll()) {
            System.out.println(toStringQuestion(question));
            System.out.println("-----------------------------------------------------------------");
        }
    }

    @Override
    public String toStringQuestion(Question question) {
        String stringQuestion = question.getQuestion();
        int number = question.getNumber();
        List<String> answerOptions = question.getAnswerOptions();

        StringBuilder sb = new StringBuilder(String.format("Question №%d:\n%s\n", number, stringQuestion));
        if (answerOptions != null && answerOptions.size() != 0) {
            sb.append("Choose from the proposed answer options (enter the number): \n");
            for (int i = 0; i < answerOptions.size(); i++) {
                sb.append(String.format("%d) %s \n", i + 1, answerOptions.get(i)));
            }
        }

        return sb.toString();
    }
}
