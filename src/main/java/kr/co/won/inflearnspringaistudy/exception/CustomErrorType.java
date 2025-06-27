package kr.co.won.inflearnspringaistudy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 어플리케이션에서 발생하는 에러에 대한 타입 정의
 */
@Getter
@AllArgsConstructor
public enum CustomErrorType {

    GEMINI_RESPONSE_ERROR(1),
    GPT_RESPONSE_ERROR(2),
    LLM_RESPONSE_JSON_PARSE_ERROR(3);

    private int code;

}
