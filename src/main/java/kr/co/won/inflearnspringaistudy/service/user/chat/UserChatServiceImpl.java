package kr.co.won.inflearnspringaistudy.service.user.chat;

import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmType;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatResponseDto;
import kr.co.won.inflearnspringaistudy.service.llmclient.LlmWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserChatServiceImpl implements UserChatService {


    private final Map<LlmType, LlmWebClientService> llmWebClientServiceMap;

    @Override
    public Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto requestDto) {
        // 호출 되면 바로 RequestDto를 만드는 것을 Mono의 흐름 안에서 생성하는 흐름으로 변경하기 위해서 defer를 이용한 객체 생성을 묶어준다.
        return Mono.defer(() -> {
            /// 사용자 요청 DTO에서 LLM Service에 전달할 DTO 객체로 변환
            LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(requestDto, "요청에 적절히 응답 해주세요.");
            Mono<LlmChatResponseDto> chatCompletionMono = llmWebClientServiceMap.get(requestDto.getLlmModel().getType())
                    .getChatCompletion(llmChatRequestDto);
            return chatCompletionMono.map(UserChatResponseDto::new);
        });
    }


    @Override
    public Flux<UserChatResponseDto> getOneShotChatStream(UserChatRequestDto requestDto) {
        LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(requestDto, "요청에 적절히 응답 해주세요.");
        Flux<LlmChatResponseDto> chatCompletionFlux = llmWebClientServiceMap.get(requestDto.getLlmModel().getType())
                .getChatCompletionStream(llmChatRequestDto);
        return chatCompletionFlux.map(UserChatResponseDto::new);
    }
}
