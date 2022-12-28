package pl.kcit.school.internal.exception;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@ToString
public class ExceptionResponse {

    private final String type;
    private final String message;
    private final OffsetDateTime dateTime;

    @Builder
    public ExceptionResponse(String type, String message, OffsetDateTime dateTime) {
        this.type = type;
        this.message = message;
        this.dateTime = dateTime;
    }

}
