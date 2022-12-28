package pl.kcit.school.internal.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResponse handleNotFoundException(NotFoundException exception) {
        return ExceptionResponse.builder()
                .type(ErrorType.NOT_FOUND.name())
                .message(exception.getMessage())
                .dateTime(OffsetDateTime.now())
                .build();
    }

    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse handleDatabaseException(DatabaseException exception) {
        return ExceptionResponse.builder()
                .type(ErrorType.DATABASE.name())
                .message(exception.getMessage())
                .dateTime(OffsetDateTime.now())
                .build();
    }

    enum ErrorType {
        NOT_FOUND,
        DATABASE
    }

}
