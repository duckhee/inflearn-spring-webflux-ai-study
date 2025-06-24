package kr.co.won.inflearnspringaistudy.service.llmclient;


import jakarta.annotation.PostConstruct;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmType;
import kr.co.won.inflearnspringaistudy.service.llmclient.gpt.request.GptChatRequestDto;
import kr.co.won.inflearnspringaistudy.service.llmclient.gpt.response.GptChatResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * GPT에서 에러가 발생했을 때 보토 4xx 에러를 발생시킨다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GptWebClientService implements LlmWebClientService {

    private final WebClient webClient;
    /// GPT에서 사용하기 위한 Key 값 -> API KEY 환경 변수 값을 읽어서 처리
    @Value("${llm.gpt.key}")
    private String gptKey;

    @PostConstruct
    public void initShow() {
        log.info("GPT API KEY : {}", gptKey);
    }

    @Override
    public Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto requestDto) {
        GptChatRequestDto gptChatRequest = new GptChatRequestDto(requestDto);
        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + gptKey) /// header에 인증 관련 API 추가
                .bodyValue(gptChatRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse) -> {
                    ///  Mono 안에서 Mono Error 를 반환하기 위해서는 flatMap을 사용해야 한다.
                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
                        log.error("[GPT ErrorResponse] {}", body);
                        /// 에러가 반환이 되면 반환 되는 시점에 Stream이 종료가 된다.
                        return Mono.error(new RuntimeException("GPT API 요청 실패 [" + body + "]"));
                    });
                }) /// 해당 method는 특정 상태가 생겼을 때 동작을 제어할 수 있다.
                .bodyToMono(GptChatResponseDto.class) // 응답을 받아올 때 객체 형태 정의
                .map(LlmChatResponseDto::new); /// 응답 객체 형태로 변환
    }

    @Override
    public LlmType getLlmType() {
        return LlmType.GPT;
    }
}
