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
import ru.otus.spring.util.exeption.DataLoadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoInFile implements QuestionDao {
    private final String questionFile;
    private final String locale;

    public QuestionDaoInFile(@Value("${dao.questionFile}") String questionFile, @Value("${application.language}") String locale) {
        this.questionFile = questionFile;
        this.locale = locale;
    }

    @Override
    public List<Question> getAll() {
        try (BufferedReader reader = getReader(questionFile)) {
            List<Question> result = new ArrayList<>();
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);
            int numberOfQuestions = 0;

            for (CSVRecord record : parser) {
                String questionLocale = record.get(0);
                int number = Integer.parseInt(record.get(1));
                if (questionLocale.equals(locale)) {
                    String question = record.get(2);
                    String rightAnswer = record.get(3);
                    Question newQuestion;
                    if (record.toList().size() > 4) {
                        newQuestion = new Question(number, QuestionType.ANSWER_OPTIONS, question, null);
                        List<String> answerOptions = record.toList().subList(4, record.toList().size());
                        List<QuestionAnswers> questionAnswers = new ArrayList<>();
                        for (int i = 0; i < answerOptions.size(); i++) {
                            boolean rightOption = rightAnswer.equals(String.valueOf(i + 1));
                            questionAnswers.add(new QuestionAnswers(i + 1, answerOptions.get(i), rightOption));
                        }
                        newQuestion.setQuestionAnswers(questionAnswers);
                    } else {
                        newQuestion = new Question(number, QuestionType.FREE_ANSWER, question, null);
                        newQuestion.setQuestionAnswers(List.of(new QuestionAnswers(1, rightAnswer, true)));
                    }
                    result.add(newQuestion);
                }
                if (numberOfQuestions < number) {
                    numberOfQuestions = number;
                }
            }

            if (result.size() != numberOfQuestions) {
                throw new DataLoadingException("Wrong question data. Not all questions have been localized.");
            }

            return result;
        } catch (IOException e) {
            throw new DataLoadingException("Wrong question data", e);
        }
    }

    private BufferedReader getReader(String questionFile) throws IOException {
        Resource resource = new ClassPathResource(questionFile);
        return new BufferedReader(new InputStreamReader(resource.getInputStream()));
    }
}
