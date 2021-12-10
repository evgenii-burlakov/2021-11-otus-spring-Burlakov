package ru.otus.spring.service.errorMessage;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.errorMessage.ErrorMessageDao;

@Service
public class ErrorMessageServiceImpl implements ErrorMessageService {
    private final ErrorMessageDao dao;

    public ErrorMessageServiceImpl(ErrorMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public String getErrorText(int number) {
        return dao.get(number).getErrorText();
    }
}
