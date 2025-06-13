package goormthonUniv.MJU.server.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    UNKNOWN_EXCEPTION_OCCURRED(500, "서버 관련 예외")
    ;

    private final int status;
    private final String message;

    ExceptionCode(int code, String message){
        this.status = code;
        this.message = message;
    }
}
