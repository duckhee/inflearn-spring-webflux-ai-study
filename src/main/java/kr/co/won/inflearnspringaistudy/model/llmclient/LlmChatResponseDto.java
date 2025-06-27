package kr.co.won.inflearnspringaistudy.model.llmclient;

import kr.co.won.inflearnspringaistudy.exception.CommonError;
import kr.co.won.inflearnspringaistudy.exception.CustomErrorType;
import kr.co.won.inflearnspringaistudy.model.llmclient.gemini.response.GeminiChatResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.gpt.response.GptChatResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LlmChatResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -6995650262910361894L;

    // chain of thought 에서 사용하는 field 값
    private String title;

    private String llmResponse;

    // Stream으로 데이터 전송 중에 중단을 하지 않기 위한 에러 검출 field
    private CommonError error;

    /**
     * 에러에 대한 값이 있는지 없는지 확인하는 함수
     *
     * @return
     */
    public boolean isValid() {
        return Optional.ofNullable(error).isEmpty();
    }

    /**
     * Chain Of Thought 를 이용할 때 응답에는 title 이 있어야 하기 때문에 해당 값을 받는 생성자
     */
    public LlmChatResponseDto(String title, String llmResponse) {
        this.title = title;
        this.llmResponse = llmResponse;
    }

    /**
     * 채팅 데이터가 정상적으로 왔을 때 사용 하는 생성자
     */
    public LlmChatResponseDto(String llmResponse) {
        this.llmResponse = llmResponse;
    }

    /**
     * 에러 발생 시 LlmChatResponseDto 객체를 생성하는 생성자
     */
    public LlmChatResponseDto(CommonError commonError) {
        log.error("[LlmChatResponseError] LlmChatResponseError: {}", commonError);
        this.error = commonError;
    }

    /**
     * 에러에 대한 StackTrace를 받아서 같이 남기는 생성자
     */
    public LlmChatResponseDto(CommonError commonError, Throwable throwable) {
        log.error("[LlmChatResponseError] LlmChatResponseError: {}", commonError, throwable);
        this.error = commonError;
    }

    /**
     * GPT에 대한 응답을 LlmChatResponseDto 객체 변경하는 생성자
     */
    public LlmChatResponseDto(GptChatResponseDto gptChatResponseDto) {
        this.llmResponse = gptChatResponseDto.getSingleChoice().getMessage().getContent();
    }

    /**
     * GPT에서 Stream 뎅터를 받기 위한 static 생성자
     */
    public static LlmChatResponseDto getLlmChatResponseDtoFromStream(GptChatResponseDto gptChatResponseDto) {
        return new LlmChatResponseDto(gptChatResponseDto.getSingleChoice().getDelta().getContent());
    }

    /**
     * Gemini에 대한 응답을 LlmChatResponseDto 객체로 변경하는 생성자
     */
    public LlmChatResponseDto(GeminiChatResponseDto geminiChatResponseDto) {
        this.llmResponse = geminiChatResponseDto.getSingleText();
    }
}
