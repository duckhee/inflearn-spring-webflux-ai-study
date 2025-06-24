package kr.co.won.inflearnspringaistudy.model.llmclient;

import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatRequestDto;
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
public class LlmChatRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7887915880214810538L;

    private String userRequest;

    /**
     * LLM이 응답할 때 지켜야 되는 행동 강렬과 같이 지켜야 하는 붑분에 대해서 명시를 해둔 prompt이다.
     * => userRequest에 있는 값보다 더 높은 강제성과 우선 순위를 가져야 한다.
     */
    private String systemPrompt;

    /**
     * LLM의 응답에 대한 형태를 json으로 설정 하는 값이다.
     * => 응답을 받아와서 바로 Java 객체로 변경하고 싶을 때 사용할 수 있다.
     */
    private Boolean useJson;

    /**
     * 사용할 LLM 모델
     */
    private LlmModel llmModel;

    /**
     * 사용자 요청으로부터 LLM 요청을 보내기 위한 DTO 만들어주는 생성자
     *
     * @param requestDto
     * @param systemPrompt
     */
    public LlmChatRequestDto(UserChatRequestDto requestDto, String systemPrompt) {
        this.userRequest = requestDto.getRequest();
        this.systemPrompt = systemPrompt;
        this.llmModel = requestDto.getLlmModel();
    }
}
