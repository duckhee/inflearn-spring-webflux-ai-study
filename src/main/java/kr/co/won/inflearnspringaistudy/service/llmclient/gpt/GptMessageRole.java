package kr.co.won.inflearnspringaistudy.service.llmclient.gpt;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.Locale;

@AllArgsConstructor
public enum GptMessageRole {

    ///  System Prompts
    SYSTEM,
    ///  사용자 입력
    USER,
    /// GPT AI 응답
    ASSISTANT;

    /// role에대한 값은 전부 소문자로 전달이 되어야 한다.
    @JsonValue
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
