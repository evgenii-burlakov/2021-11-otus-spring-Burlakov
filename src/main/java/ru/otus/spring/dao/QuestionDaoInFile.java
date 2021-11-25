package ru.otus.spring.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.spring.domain.Question;
import ru.otus.spring.util.exeption.ApplicationError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoInFile implements QuestionDao {
    private final String questionFile;

    public QuestionDaoInFile(String questionFile) {
        this.questionFile = questionFile;
    }

    @Override
    public List<Question> getAll() {
        try {
            BufferedReader reader = getReader(questionFile);
            List<Question> result = new ArrayList<>();
            while (reader.ready()) {
                String line = reader.readLine();
                CSVParser parser = CSVParser.parse(line, CSVFormat.DEFAULT);
                for (CSVRecord record : parser) {
                    if (record.toList().size() > 1) {
                        int number;
                        try {
                            number = Integer.parseInt(record.get(0));
                        } catch (NumberFormatException e) {
                            throw new ApplicationError("Wrong question number format");
                        }

                        String question = record.get(1);
                        List<String> answerOptions = record.toList().size() > 2 ?
                                new ArrayList<>(record.toList().subList(2, record.toList().size())) : null;

                        result.add(new Question(number, question, answerOptions));
                    } else throw new ApplicationError("Wrong question data");
                }
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private BufferedReader getReader(String questionFile) throws IOException {
        Resource resource = new ClassPathResource(questionFile);
        return new BufferedReader(new InputStreamReader(resource.getInputStream()));
    }
}
