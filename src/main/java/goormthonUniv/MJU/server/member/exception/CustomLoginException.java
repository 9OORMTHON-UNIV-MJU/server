package goormthonUniv.MJU.server.member.exception;

import lombok.Getter;
import java.time.Instant;

import goormthonUniv.MJU.server.global.exception.ExceptionCode;

@Getter
public class CustomLoginException extends RuntimeException {

    private final ExceptionCode exceptionCode;
    private final Instant occurredAt;

    public CustomLoginException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
        this.occurredAt = Instant.now();
    }
    public CustomLoginException(ExceptionCode exceptionCode, String message){
        super(exceptionCode.getMessage() + "| 상세 오류 :" + message);
        this.exceptionCode = exceptionCode;
        this.occurredAt = Instant.now();
    }
}
