package com.bike.bikeproject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Controller 에서 발생하는 모든 예외를 한 번에 로깅 및
     * 예외에 따라 올바르게 처리해줄 수 있게끔 작성함
     * @param exception RestController 에서 발생하는 모든 Exception 을 처리
     * @return ResponseEntity 는 오류에 따라 다른 Status 를 반환해야 함
     * todo: 오류에 따른 HttpStatus 응답 메시지 분기 처리 or 메소드 분리
     */
    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<String> handleAllExceptions(Exception exception) {
        log.error("Exception has occurred: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
