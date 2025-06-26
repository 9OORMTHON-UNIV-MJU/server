package goormthonUniv.MJU.server.global.resolver.exception;

import goormthonUniv.MJU.server.global.exception.ExceptionCode;
import java.time.Instant;

public class GlobalException extends RuntimeException{

    private final ExceptionCode exceptionCode;
    private final Instant occurredAt;

    public GlobalException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
        this.occurredAt = Instant.now();
    }

    public GlobalException(ExceptionCode exceptionCode, String message){
        super(exceptionCode.getMessage() + "| 상세 오류 :" + message);
        this.exceptionCode = exceptionCode;
        this.occurredAt = Instant.now();
    }
}
