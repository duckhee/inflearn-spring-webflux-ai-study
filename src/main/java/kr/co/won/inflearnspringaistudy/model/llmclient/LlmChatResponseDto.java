package kr.co.won.inflearnspringaistudy.model.llmclient;

import kr.co.won.inflearnspringaistudy.exception.CommonError;
import kr.co.won.inflearnspringaistudy.exception.CustomErrorType;
import kr.co.won.inflearnspringaistudy.model.llmclient.gemini.response.GeminiChatResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.gpt.response.GptChatResponseDto;
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
public class LlmChatResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -6995650262910361894L;

    private String llmResponse;

    // Stream으로 데이터 전송 중에 중단을 하지 않기 위한 에러 검출 field
    private CommonError error;

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
