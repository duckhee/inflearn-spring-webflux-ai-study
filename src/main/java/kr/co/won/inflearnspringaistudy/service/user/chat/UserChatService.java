package kr.co.won.inflearnspringaistudy.service.user.chat;

import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserChatService {

    /**
     * 요청에 대한 채팅 결과를 전달하는 함수
     *
     * @param requestDto
     * @return
     */
    Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto requestDto);

    /**
     * HTTP Stream 형태로 데이터를 전달하는 함수
     *
     * @param requestDto
     * @return
     */
    Flux<UserChatResponseDto> getOneShotChatStream(UserChatRequestDto requestDto);
}
