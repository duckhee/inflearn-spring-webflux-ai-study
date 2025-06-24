package kr.co.won.inflearnspringaistudy.model.llmclient;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LlmModel {
    GPT_4O("gpt-4o", LlmType.GPT),
    GEMINI_2_0_FLASH("gemini-2.0-flash", LlmType.GEMINI);

    private final String code;

    private final LlmType type;
}
