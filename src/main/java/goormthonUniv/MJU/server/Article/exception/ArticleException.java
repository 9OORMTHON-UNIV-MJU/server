package goormthonUniv.MJU.server.Article.exception;

import java.time.Instant;
import lombok.Getter;

@Getter
public class ArticleException extends RuntimeException{

    private final ArticleErrorCode errorCode;
    private final Instant occurredAt;

    public ArticleException(ArticleErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.occurredAt = Instant.now();
    }
    public ArticleException(ArticleErrorCode errorCode, String message){
        super(errorCode.getMessage() + "| 상세 오류 :" + message);
        this.errorCode = errorCode;
        this.occurredAt = Instant.now();
    }
}
