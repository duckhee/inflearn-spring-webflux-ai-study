package kr.co.won.inflearnspringaistudy.service.user.chat;

import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatResponseDto;
import reactor.core.publisher.Flux;

public interface ChainOfThoughtService {

    /**
     * LLM에게 단계 별로 요청하고 단계 별 처리를 한 다음에 최종 응답을 하도록 Chain 형태로 답변하는 함수
     *
     * @param userChatRequestDto
     * @return
     */
    Flux<UserChatResponseDto> getChainOfThoughtResponse(UserChatRequestDto userChatRequestDto);
}
