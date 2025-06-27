package kr.co.won.inflearnspringaistudy.model.llmclient.gemini.request;

import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.gemini.GeminiMessageRole;
import kr.co.won.inflearnspringaistudy.model.llmclient.gemini.response.GeminiContent;
import kr.co.won.inflearnspringaistudy.model.llmclient.gemini.response.GeminiPart;
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
public class GeminiChatRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 5123434336972745640L;

    /// Genmini에서 여러 요청에 대해서 받아서 처리하기 위한 변수
    private List<GeminiContent> contents;

    ///  system prompt에 대한 값을 넣어주는 변수 -> Camel case를 사용한다.
    private GeminiContent systemInstruction;

    /// 응답에 대한 타입을 지정하는 변수
    private GeminiGenerationConfigDto generationConfig;


    public GeminiChatRequestDto(LlmChatRequestDto requestDto) {

        /// json 형태로 값을 사용한다고 되어 있을 때만 사용
        if (requestDto.getUseJson() != null && requestDto.getUseJson()) {
            this.generationConfig = new GeminiGenerationConfigDto();
        }

        this.contents = List.of(
                new GeminiContent(List.of(
                        new GeminiPart(requestDto.getUserRequest())
                ), GeminiMessageRole.USER)
        );
        this.systemInstruction = new GeminiContent(
                List.of(
                        new GeminiPart(requestDto.getSystemPrompt())
                )
        );
    }
}
