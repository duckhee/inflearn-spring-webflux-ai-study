package kr.co.won.inflearnspringaistudy.service.user.chat;

import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserChatServiceImpl implements UserChatService {

    @Override
    public Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto requestDto) {
        /// 사용자 요청 DTO에서 LLM Service에 전달할 DTO 객체로 변환
        LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(requestDto, "요청에 적절히 응답 해주세요.");
        return null;
    }
}
