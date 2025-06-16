package goormthonUniv.MJU.server.global.exception;

import goormthonUniv.MJU.server.Article.exception.ArticleErrorCode;
import goormthonUniv.MJU.server.Article.exception.ArticleException;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class ErrorResponse {

    private final Integer status;
    private final String code;
    private final String message;
    private final Instant occurredAt;

    // 생성자 (@Builder가 생성해주지만, 직접 생성자 만들 수도 있음)
    public ErrorResponse(Integer status, String code, String message, Instant occurredAt) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.occurredAt = occurredAt;
    }

    public static ErrorResponse of(CustomLoginException exception) {
        ExceptionCode code = exception.getExceptionCode();
        return ErrorResponse.builder()
                .status(code.getStatus())
                .code(code.name())
                .message(code.getMessage())
                .occurredAt(exception.getOccurredAt())
                .build();
    }

    public static ErrorResponse of(ArticleException exception) {
        ArticleErrorCode errorCode = exception.getErrorCode();
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .occurredAt(exception.getOccurredAt())
                .build();
    }
}
