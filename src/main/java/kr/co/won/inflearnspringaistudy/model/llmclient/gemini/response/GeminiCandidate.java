package kr.co.won.inflearnspringaistudy.model.llmclient.gemini.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeminiCandidate implements Serializable {
    @Serial
    private static final long serialVersionUID = -8899274160113146597L;

    /// LLM에 대한 응답 값이 담겨 있는 변수
    private GeminiContent content;
}
