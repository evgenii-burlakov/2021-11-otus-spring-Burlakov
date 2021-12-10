package ru.otus.spring.dao.question;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionAnswers;
import ru.otus.spring.domain.QuestionType;
import ru.otus.spring.util.exeption.QuestionsLoadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoInFile implements QuestionDao {
    private final String questionFile;

    public QuestionDaoInFile(@Value("${dao.questionFile}") String questionFile) {
        this.questionFile = questionFile;
    }

    @Override
    public List<Question> getAll() {
        try (BufferedReader reader = getReader(questionFile)) {
            List<Question> result = new ArrayList<>();
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

            for (CSVRecord record : parser) {
                if (record.toList().size() > 2) {
                    int number = Integer.parseInt(record.get(0));
                    String question = record.get(1);
                    String rightAnswer = record.get(2);
                    Question newQuestion;
                    if (record.toList().size() > 3) {
                        newQuestion = new Question(number, QuestionType.ANSWER_OPTIONS, question, null);
                        List<String> answerOptions = record.toList().subList(3, record.toList().size());
                        List<QuestionAnswers> questionAnswers = new ArrayList<>();
                        for (int i = 0; i < answerOptions.size(); i++) {
                            boolean rightOption = rightAnswer.equals(String.valueOf(i + 1));
                            questionAnswers.add(new QuestionAnswers(i + 1, answerOptions.get(i), rightOption));
                        }
                        newQuestion.setAnswerOptions(questionAnswers);
                    } else {
                        newQuestion = new Question(number, QuestionType.FREE_ANSWER, question, null);
                        newQuestion.setAnswerOptions(List.of(new QuestionAnswers(1, rightAnswer, true)));
                    }
                    result.add(newQuestion);
                } else {
                    throw new QuestionsLoadingException("Wrong question data");
                }
            }

            return result;
        } catch (Exception e) {
            throw new QuestionsLoadingException("Wrong question data");
        }
    }

    private BufferedReader getReader(String questionFile) throws IOException {
        Resource resource = new ClassPathResource(questionFile);
        return new BufferedReader(new InputStreamReader(resource.getInputStream()));
    }
}
