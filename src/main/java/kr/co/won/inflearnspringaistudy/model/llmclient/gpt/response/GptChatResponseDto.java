package kr.co.won.inflearnspringaistudy.model.llmclient.gpt.response;

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
public class GptChatResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6229801842914192659L;

    /// GPT에서 choices라는 field의 이름으로 여러 가지 응답 옵션들이 리스트로 담겨서 넘어오게 된다.
    /// 여러 응답을 하는 경우가 있기 때문에 List로 받아와야 한다.
    private List<GptChoice> choices;


    public GptChoice getSingleChoice() {
        return this.choices.stream().findFirst().orElseThrow(() -> new ErrorTypeException("[GPT Response] There is no choices.", CustomErrorType.GPT_RESPONSE_ERROR));
    }
}
