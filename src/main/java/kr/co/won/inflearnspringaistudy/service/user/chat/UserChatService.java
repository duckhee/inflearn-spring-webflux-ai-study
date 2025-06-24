package kr.co.won.inflearnspringaistudy.service.user.chat;

import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatResponseDto;
import reactor.core.publisher.Mono;

public interface UserChatService {

    Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto requestDto);
}
