package kr.co.won.inflearnspringaistudy.model.llmclient;

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

    /**
     * GPT에 대한 응답을 LlmChatResponseDto 객체 변경하는 생성자
     */
    public LlmChatResponseDto(GptChatResponseDto gptChatResponseDto) {
        this.llmResponse = gptChatResponseDto.getSingleChoice().getMessage().getContent();
    }

    /**
     * Gemini에 대한 응답을 LlmChatResponseDto 객체로 변경하는 생성자
     */
    public LlmChatResponseDto(GeminiChatResponseDto geminiChatResponseDto) {
        this.llmResponse = geminiChatResponseDto.getSingleText();
    }
}
