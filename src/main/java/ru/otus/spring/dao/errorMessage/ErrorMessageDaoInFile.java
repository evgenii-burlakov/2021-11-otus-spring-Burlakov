package ru.otus.spring.dao.errorMessage;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.ErrorMessage;
import ru.otus.spring.util.exeption.QuestionsLoadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ErrorMessageDaoInFile implements ErrorMessageDao {
    private final String errorMessage;

    public ErrorMessageDaoInFile(@Value("${dao.errorMessagesFile}") String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public ErrorMessage get(int number) {
        List<ErrorMessage> errorMessages = getAll();

        if (errorMessages.size() >= number) {
            return errorMessages.get(number - 1);
        }

        return null;
    }

    public List<ErrorMessage> getAll() {
        try (BufferedReader reader = getReader(errorMessage)) {
            List<ErrorMessage> result = new ArrayList<>();
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

            for (CSVRecord record : parser) {
                if (record.toList().size() == 2) {
                    int number = Integer.parseInt(record.get(0));
                    String errorMessage = record.get(1);
                    result.add(new ErrorMessage(number, errorMessage));
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
