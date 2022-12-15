package pl.kcit.school.internal.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InternalBusinessException extends RuntimeException {

    private final ErrorType type;

    @Builder
    private InternalBusinessException(String message, ErrorType type) {
        super(message);
        this.type = type;
    }

}
