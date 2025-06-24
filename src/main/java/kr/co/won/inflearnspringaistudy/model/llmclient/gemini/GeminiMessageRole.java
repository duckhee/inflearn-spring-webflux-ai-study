package kr.co.won.inflearnspringaistudy.model.llmclient.gemini;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GeminiMessageRole {

    USER,
    MODEL;

    /**
     * 직렬화할 떄에는 모두 소문자여야 해서 override를 통한 재정의
     *
     * @return
     */
    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
