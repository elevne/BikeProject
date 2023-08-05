package com.bike.bikeproject.exception;

import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * .txt 파일을 읽고 Batch Insert 작업을 진행하는 부분에서 잘못된 파일 형식 등의 이유로 발생할 수 있는 예외
     * @param ioException @RestController 에서 발생하는 IOException
     * @return HttpStatus Code 500
     */
    @ExceptionHandler(IOException.class)
    protected final ResponseEntity<String> handleIOException(IOException ioException) {
        log.error("Exception Occurred: ({}: {})", ioException.getClass().getSimpleName(), ioException.getMessage());
        return new ResponseEntity<>("ERROR READING FILE", HttpStatus.INTERNAL_SERVER_ERROR);  // 500
    }

    /**
     * 클라이언트에서 잘못된 (규약에 맞지 않는) 값을 파라미터로 넘겼을 때 발생할 수 있는 예외
     * @param illegalArgumentException @RestController 에서 발생하는 IllegalArgumentException
     * @return HttpStatus Code 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected final ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        log.error("Exception Occurred: ({}: {})", illegalArgumentException.getClass().getSimpleName(), illegalArgumentException.getMessage());
        return new ResponseEntity<>("BAD PARAMETER PASSED IN", HttpStatus.BAD_REQUEST);  // 400
    }

    // todo: HttpStatus code 및 예외 다시 확인해보기
    /**
     * 서울 따릉이 API 호출 중 문제가 발생했을 경우 던져지는 예외
     * @param bikeAPIException @RestController 에서 발생하는 BikeAPIException
     * @return HttpStatus Code 502 or 504
     */
    @ExceptionHandler(BikeAPIException.class)
    protected final ResponseEntity<String> handleBikeAPIException(BikeAPIException bikeAPIException) {
        log.error("Exception Occurred ({}: {})", bikeAPIException.getClass().getSimpleName(), bikeAPIException.getMessage());
        if (bikeAPIException.getCause().getClass().equals(JsonSyntaxException.class)) {
            return new ResponseEntity<>("BAD RESPONSE FROM BIKE API", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>("ERROR CALLING SEOUL BIKE API", HttpStatus.GATEWAY_TIMEOUT);  // 504
    }

    /**
     * 위 예외를 제외하고 발생할 수 있는 예외들을 전부 로깅 처리
     * @param exception @RestController 에서 발생하는 모든 Exception 을 처리
     * @return HttpStatus Code 500
     */
    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<String> handleAllExceptions(Exception exception) {
        log.error("Exception Occurred: ({}: {})", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
