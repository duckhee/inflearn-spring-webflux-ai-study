package kr.co.won.inflearnspringaistudy.service.user.chat;

import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmType;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatResponseDto;
import kr.co.won.inflearnspringaistudy.service.llmclient.LlmWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserChatServiceImpl implements UserChatService {

    private final Map<LlmType, LlmWebClientService> llmWebClientServiceMap;

    @Override
    public Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto requestDto) {
        /// 사용자 요청 DTO에서 LLM Service에 전달할 DTO 객체로 변환
        LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(requestDto, "요청에 적절히 응답 해주세요.");
        Mono<LlmChatResponseDto> chatCompletionMono = llmWebClientServiceMap.get(requestDto.getLlmModel().getType())
                .getChatCompletion(llmChatRequestDto);
        return chatCompletionMono.map(UserChatResponseDto::new);
    }
}
