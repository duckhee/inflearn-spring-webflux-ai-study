package kr.co.won.inflearnspringaistudy.service.llmclient;

import jakarta.annotation.PostConstruct;
import kr.co.won.inflearnspringaistudy.exception.CommonError;
import kr.co.won.inflearnspringaistudy.exception.CustomErrorType;
import kr.co.won.inflearnspringaistudy.exception.ErrorTypeException;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmType;
import kr.co.won.inflearnspringaistudy.model.llmclient.gemini.request.GeminiChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.gemini.response.GeminiChatResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiWebClientService implements LlmWebClientService {

    private final WebClient webClient;

    private final static String GEMINI_BASE_ONE_SHOT_REQUEST_URI = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    private final static String GEMINI_BASE_STREAM_REQUEST_URI = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:streamGenerateContent?key=";

    @Value("${llm.gemini.key}")
    private String key;


    @PostConstruct
    public void initShow() {
        log.info("GEMINI API KEY : {}", key);
    }

    @Override
    public Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto requestDto) {
        GeminiChatRequestDto geminiChatRequestDto = new GeminiChatRequestDto(requestDto);
        return webClient.post()
                .uri(GEMINI_BASE_ONE_SHOT_REQUEST_URI + key)
                .bodyValue(geminiChatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    log.error("[GEMINI ErrorResponse] {}", clientResponse);
                    return Mono.error(new ErrorTypeException("GEMINI API 요청 실패 [" + clientResponse + "]", CustomErrorType.GEMINI_RESPONSE_ERROR));
                }))
                .bodyToMono(GeminiChatResponseDto.class)
                .map(LlmChatResponseDto::new);
    }

    @Override
    public LlmType getLlmType() {
        return LlmType.GEMINI;
    }

    @Override
    public Flux<LlmChatResponseDto> getChatCompletionStream(LlmChatRequestDto llmChatRequestDto) {
        GeminiChatRequestDto geminiChatRequestDto = new GeminiChatRequestDto(llmChatRequestDto);
        AtomicInteger counter = new AtomicInteger(0);
        return webClient.post()
                .uri(GEMINI_BASE_STREAM_REQUEST_URI + key)
                .bodyValue(geminiChatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    log.error("[GEMINI ErrorResponse] {}", clientResponse);
                    return Mono.error(new ErrorTypeException("GEMINI API 요청 실패 [" + clientResponse + "]", CustomErrorType.GEMINI_RESPONSE_ERROR));
                }))
                .bodyToFlux(GeminiChatResponseDto.class)
                .map(LlmChatResponseDto::new);
//                .map(res -> {
//            if (counter.incrementAndGet() % 5 == 0) {
//                throw new ErrorTypeException("test error", CustomErrorType.GEMINI_RESPONSE_ERROR);
//            }
//            return new LlmChatResponseDto(res);
//        })
//                .map(res -> {
//                    try {
//                        if (counter.incrementAndGet() % 5 == 0) {
//                            throw new ErrorTypeException("test error", CustomErrorType.GEMINI_RESPONSE_ERROR);
//                        }
//                        return new LlmChatResponseDto(res);
//                    } catch (ErrorTypeException e) {
//                        return new LlmChatResponseDto(new CommonError(e.getErrorType().getCode(), e.getMessage()));
//                    }
//                });
//                .map(LlmChatResponseDto::new);
    }
}
