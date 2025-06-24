package kr.co.won.inflearnspringaistudy.controller.user.chat;

import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatResponseDto;
import kr.co.won.inflearnspringaistudy.service.user.chat.UserChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/chat")
@RequiredArgsConstructor
public class UserChatController {

    private final UserChatService userChatService;

    // 유저가 요청 하나를 하면 단일 응답을 반환하는 역할

    /**
     * 사용자 요청에 따른 단일 응답 하는 API
     *
     * @param requestDto 사용자 요청과 사용할 LLM Model에 대한 정보를 담고 있는 Dto
     * @return 문자열 형태로 되어 있는 Dto
     */
    @PostMapping(path = "/oneshot")
    public Mono<UserChatResponseDto> oneShotChat(@RequestBody UserChatRequestDto requestDto) {
        // service에서 데이터 가공해서 응답을 response로 돌려준다.
        return userChatService.getOneShotChat(requestDto);
    }
}
