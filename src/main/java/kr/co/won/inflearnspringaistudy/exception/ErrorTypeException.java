package kr.co.won.inflearnspringaistudy.exception;

import java.io.Serial;

/**
 * Application에서 사용하는 Custom Error에 대한 정의
 */
public class ErrorTypeException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = -7251103027650433836l;

    // 에러에 대한 타입 정보
    private final CustomErrorType errorType;

    public ErrorTypeException(String message, CustomErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public CustomErrorType getErrorType() {
        return errorType;
    }

    @Override
    public String getMessage() {
        return "ExceptionCode : [" + errorType.getCode() + "], Message: [" + super.getMessage() + "]";
    }
}
