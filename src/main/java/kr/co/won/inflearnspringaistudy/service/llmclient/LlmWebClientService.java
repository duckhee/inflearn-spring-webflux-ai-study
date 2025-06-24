package kr.co.won.inflearnspringaistudy.service.llmclient;

import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmType;
import reactor.core.publisher.Mono;

public interface LlmWebClientService {

    /**
     * LLM에 요청을 보내서 응답을 가져오는 기능
     *
     * @param requestDto
     * @return
     */
    Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto requestDto);

    /**
     * 사용할 Service에 대한 타입을 가져와서 특정 Service를 실행하기 위해서 구분이 되는 값을 가져오기 위한 함수
     *
     * @return
     */
    LlmType getLlmType();
}
