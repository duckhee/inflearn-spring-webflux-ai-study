package kr.co.won.inflearnspringaistudy.model.user.chat;

import kr.co.won.inflearnspringaistudy.exception.CommonError;
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

    // chain of thought 에서 추가된 Field이다.
    private String title;

    private String response;

    private CommonError error;


    /**
     * LLM Service 응답을 UserChatResponseDto 형태로 생성자를 이용한 변환
     */
    public UserChatResponseDto(LlmChatResponseDto llmChatResponseDto) {
        this.title = llmChatResponseDto.getTitle();
        this.response = llmChatResponseDto.getLlmResponse();
        this.error = llmChatResponseDto.getError();
    }

    /**
     * Chain Of Thought 를 이용할 때 사용할 생성자
     */
    public UserChatResponseDto(String response, String title) {
        this.response = response;
        this.title = title;
    }
}
