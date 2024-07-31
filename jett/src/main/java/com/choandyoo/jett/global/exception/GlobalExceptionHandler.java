package com.choandyoo.jett.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.choandyoo.jett.member.exception.DuplicateEmailException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /* DuplicateEmailException */
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException ex) {
        // 로깅 추가
        log.error("DuplicateEmailException 발생, 메시지: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
            501, // 또는 적절한 에러 코드
            ex.getMessage()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // ErrorResponse 클래스는 예외 응답을 위한 포맷을 정의합니다.
    public static class ErrorResponse {
        private int errorCode;
        private String message;

        public ErrorResponse(int errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }

        // Getter
        public int getErrorCode() {
            return errorCode;
        }

        public String getMessage() {
            return message;
        }

        // Setter
        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
