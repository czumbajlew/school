package pl.kcit.school.internal.exception;

import lombok.*;

@Getter
@ToString
public class ExceptionResponse {

    private final String type;
    private final String message;
    private final String dateTime;

    @Builder
    public ExceptionResponse(String type, String message, String dateTime) {
        this.type = type;
        this.message = message;
        this.dateTime = dateTime;
    }

}
