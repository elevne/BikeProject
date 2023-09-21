package com.bike.bikeproject.exception;

/*
Authentication 시 일어나는 각종 예외상황들을 커버할 추상 예외 클래스
 */
public class JwtAuthException extends RuntimeException {

    public JwtAuthException(String cause) {
        super(cause);
    }

    public JwtAuthException(Throwable cause) {
        super(cause);
    }
}
