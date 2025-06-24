package goormthonUniv.MJU.server.member.exception;

import java.time.Instant;

import goormthonUniv.MJU.server.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class CustomRegisterException extends RuntimeException {

	private final ExceptionCode exceptionCode;
    private final Instant occurredAt;

    public CustomRegisterException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
        this.occurredAt = Instant.now();
    }
    public CustomRegisterException(ExceptionCode exceptionCode, String message){
        super(exceptionCode.getMessage() + "| 상세 오류 :" + message);
        this.exceptionCode = exceptionCode;
        this.occurredAt = Instant.now();
    }
	
}
