package kr.co.won.inflearnspringaistudy.model.llmclient.gemini.request;

import kr.co.won.inflearnspringaistudy.model.llmclient.gemini.GeminiMessageRole;
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
public class GeminiCompletionRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7974235014360568258L;

    /// 누가 보냈는지 나타내는 변수
    private GeminiMessageRole role;

    ///  요청에 대한 값 변수
    private String content;

    /**
     * System Prompts에 대한 설정 시 사용하는 생성자
     *
     * @param content
     */
    public GeminiCompletionRequestDto(String content) {
        this.content = content;
    }
}
