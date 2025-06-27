package kr.co.won.inflearnspringaistudy.model.llmclient.gemini.response;

import kr.co.won.inflearnspringaistudy.model.llmclient.gemini.GeminiMessageRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeminiContent implements Serializable {
    @Serial
    private static final long serialVersionUID = 8868251774805510579L;

    ///  응답에 대한 값을 가지고 있는 변수
    private List<GeminiPart> parts;

    private GeminiMessageRole role;

    public GeminiContent(List<GeminiPart> parts) {
        this.parts = parts;
    }
}
