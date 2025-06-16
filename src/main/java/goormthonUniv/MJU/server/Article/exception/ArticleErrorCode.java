package goormthonUniv.MJU.server.Article.exception;

import lombok.Getter;

@Getter
public enum ArticleErrorCode {

    NOT_FOUND_ARTICLE(404, "게시글을 찾을 수 없습니다.")
    ;

    private final int status;
    private final String message;

    ArticleErrorCode(int code, String message){
        this.status = code;
        this.message = message;
    }
}
