package kr.co.won.inflearnspringaistudy.service.llmclient.gpt.request;

import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmModel;
import kr.co.won.inflearnspringaistudy.service.llmclient.gpt.GptMessageRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GptChatRequestDto implements Serializable {


    @Serial
    private static final long serialVersionUID = -8895238078970729065L;

    /// 사용할 LLM Model에 대한 값
    private LlmModel model;

    ///  Stream 형식으로 응답을 받을지 아닐지 설정하는 값
    private Boolean stream;

    /// 이전 채팅에 대한 컨텍스트를 유지하면서 대화를 하고 싶은 경우 이전 대화까지 리스트로 묶어서 보내야 하기 때문이다.
    private List<GptCompletionRequestDto> message;

    /// 응답에 대한 형태 정의 변수 -> GPT API 문서를 확인하면 sneak case로 정의가 되어 있다.
    private GptResponseFormat response_format;

    /**
     * 서비스에서 응답을 받아서 GPT Request에 대한 객체로 변환해주는 생성자
     */
    public GptChatRequestDto(LlmChatRequestDto requestDto) {
        /// useJson에 대한 값이 존재하는지 확인하는 부분
        if (Optional.ofNullable(requestDto.getUseJson()).filter(useJson -> useJson).isPresent()) {
            this.response_format = new GptResponseFormat("json_object");
        }

        this.message = List.of(
                new GptCompletionRequestDto(requestDto.getSystemPrompt(), GptMessageRole.SYSTEM), ///  system prompt에 대한 입력 생성
                new GptCompletionRequestDto(requestDto.getUserRequest(), GptMessageRole.USER) /// 사용자가 입력한 메시지
        );
        /// 응답할 GPT Model 설정
        this.model = requestDto.getLlmModel();

    }
}
