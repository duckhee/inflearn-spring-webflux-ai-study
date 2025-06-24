package kr.co.won.inflearnspringaistudy.service.llmclient;

import jakarta.annotation.PostConstruct;
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
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiWebClientService implements LlmWebClientService {

    private final WebClient webClient;

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
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + key)
                .bodyValue(geminiChatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    log.error("[GPT ErrorResponse] {}", clientResponse);
                    return Mono.error(new RuntimeException("GPT API 요청 실패 [" + clientResponse + "]"));
                }))
                .bodyToMono(GeminiChatResponseDto.class)
                .map(LlmChatResponseDto::new);
    }

    @Override
    public LlmType getLlmType() {
        return LlmType.GEMINI;
    }
}
