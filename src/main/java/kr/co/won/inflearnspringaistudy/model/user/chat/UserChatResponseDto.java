package kr.co.won.inflearnspringaistudy.model.user.chat;

import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatResponseDto;
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
public class UserChatResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -5992809430859628022L;

    private String response;

    /**
     * LLM Service 응답을 UserChatResponseDto 형태로 생성자를 이용한 변환
     */
    public UserChatResponseDto(LlmChatResponseDto llmChatResponseDto) {
        this.response = llmChatResponseDto.getLlmResponse();
    }
}
