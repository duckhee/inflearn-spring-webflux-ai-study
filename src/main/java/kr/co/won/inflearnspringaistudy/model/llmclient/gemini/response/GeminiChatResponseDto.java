package kr.co.won.inflearnspringaistudy.model.llmclient.gemini.response;

import kr.co.won.inflearnspringaistudy.exception.CustomErrorType;
import kr.co.won.inflearnspringaistudy.exception.ErrorTypeException;
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
public class GeminiChatResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 8369527640858521246L;

    /// 응답에 대해서 여러 형태로 오기 때문에 받아주기 위한 리스트 배열 변수
    private List<GeminiCandidate> candidates;

    public String getSingleText() {
        return candidates
                .stream()
                .findFirst()
                .flatMap(
                        candidate -> candidate.getContent().getParts()
                                .stream().findFirst().map(part -> part.getText())
                )
                .orElseThrow(() -> new ErrorTypeException("[GeminiResponse] There is no candidates. ", CustomErrorType.GEMINI_RESPONSE_ERROR));
    }
}
