package kr.co.won.inflearnspringaistudy.exception;

import kr.co.won.inflearnspringaistudy.InflearnSpringAiStudyApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice(basePackageClasses = InflearnSpringAiStudyApplication.class)
public class GlobalExceptionHandler {


    /**
     * 기존의 jakarta / javax 기반의 서블릿 객체를 받아서 사용했지만, webFlux에서는 Exchange를 이용해서 필요한 정보를 얻어올 수 있다.
     *
     * @param ex
     * @param exchange
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleGeneralException(Exception ex, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[GeneralException] Request IP : [{}] Request URI :[{}], METHOD : [{}], Error : [{}]", request.getRemoteAddress(), request.getURI(), request.getMethod(), ex.getMessage(), ex);
        CommonError commonError = new CommonError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return Mono.just(new ErrorResponse(commonError));
    }

    /**
     * 사용자가 정의한 에러에 대한 처리하는 Handler
     *
     * @param ex
     * @param exchange
     * @return
     */
    @ExceptionHandler(ErrorTypeException.class)
    public Mono<ErrorResponse> handleErrorTypeException(ErrorTypeException ex, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[ErrorTypeException] Request IP : [{}] Request URI :[{}], METHOD : [{}], Error : [{}]", request.getRemoteAddress(), request.getURI(), request.getMethod(), ex.getMessage(), ex);
        CommonError commonError = new CommonError(ex.getErrorType().getCode(), ex.getMessage());
        return Mono.just(new ErrorResponse(commonError));
    }
}
