package goormthonUniv.MJU.server.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	// 로그인 관련 예외
    MEMBER_NOT_FOUND(404, "닉네임 존재하지 않음"),
    INVALID_PASSWORD(401, "비밀번호 불일치"),
    
    // 회원가입 관련 예외 추가
    DUPLICATE_NICKNAME(409, "닉네임 중복"),
    
    UNKNOWN_EXCEPTION_OCCURRED(500, "서버 관련 예외")
    ;

    private final int status;
    private final String message;

    ExceptionCode(int code, String message){
        this.status = code;
        this.message = message;
    }
}
