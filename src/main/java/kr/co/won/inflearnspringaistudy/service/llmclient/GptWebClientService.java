package kr.co.won.inflearnspringaistudy.service.llmclient;


import jakarta.annotation.PostConstruct;
import kr.co.won.inflearnspringaistudy.exception.CustomErrorType;
import kr.co.won.inflearnspringaistudy.exception.ErrorTypeException;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmType;

import kr.co.won.inflearnspringaistudy.model.llmclient.gpt.request.GptChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.gpt.response.GptChatResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * GPT에서 에러가 발생했을 때 보토 4xx 에러를 발생시킨다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GptWebClientService implements LlmWebClientService {

    private static final String GPT_API_URI = "https://api.openai.com/v1/chat/completions";

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
                .uri(GPT_API_URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + gptKey) /// header에 인증 관련 API 추가
                .bodyValue(gptChatRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse) -> {
                    ///  Mono 안에서 Mono Error 를 반환하기 위해서는 flatMap을 사용해야 한다.
                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
                        log.error("[GPT ErrorResponse] {}", body);
                        /// 에러가 반환이 되면 반환 되는 시점에 Stream이 종료가 된다.
                        return Mono.error(new ErrorTypeException("GPT API 요청 실패 [" + body + "]", CustomErrorType.GPT_RESPONSE_ERROR));
                    });
                }) /// 해당 method는 특정 상태가 생겼을 때 동작을 제어할 수 있다.
                .bodyToMono(GptChatResponseDto.class) // 응답을 받아올 때 객체 형태 정의
                .map(LlmChatResponseDto::new); /// 응답 객체 형태로 변환
    }

    @Override
    public LlmType getLlmType() {
        return LlmType.GPT;
    }

    @Override
    public Flux<LlmChatResponseDto> getChatCompletionStream(LlmChatRequestDto llmChatRequestDto) {
        GptChatRequestDto gptChatRequestDto = new GptChatRequestDto(llmChatRequestDto);
        // Stream 형태로 응답을 받기 위한 API 명세에 정의가 되어 있는 값 설정
        gptChatRequestDto.setStream(true);
        return webClient.post()
                .uri(GPT_API_URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + gptKey)
                .bodyValue(gptChatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse) -> {
                    log.error("[Get Stream ErrorResponse] {}", clientResponse);
                    return Mono.error(new ErrorTypeException("Get Stream Error", CustomErrorType.GPT_RESPONSE_ERROR));
                })
                .bodyToFlux(GptChatResponseDto.class)
                .takeWhile(response -> Optional.ofNullable(response.getSingleChoice().getFinish_reason()).isEmpty()) // 해당 조건이 만족하면 다운 스트림을 동작하고 해당 조건이 만족하지 않으면 다운 스트림이 실행이 되지 않는다.
                .map(LlmChatResponseDto::getLlmChatResponseDtoFromStream);

    }
}
