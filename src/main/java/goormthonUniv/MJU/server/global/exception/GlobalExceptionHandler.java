package goormthonUniv.MJU.server.global.exception;

import goormthonUniv.MJU.server.Article.exception.ArticleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomLoginException.class)
    public ResponseEntity<ErrorResponse> handleCustomExceptions(CustomLoginException exception){
        ErrorResponse response = ErrorResponse.of(exception);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(ArticleException.class)
    public ResponseEntity<ErrorResponse> handleArticleExceptions(ArticleException exception){
        ErrorResponse response = ErrorResponse.of(exception);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnKnownExceptions(RuntimeException exception){

        ErrorResponse response = ErrorResponse.of(
                new CustomLoginException(ExceptionCode.UNKNOWN_EXCEPTION_OCCURRED, exception.getMessage())
        );

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
}
