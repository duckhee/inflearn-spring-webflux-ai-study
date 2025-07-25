package kr.co.won.inflearnspringaistudy.service.llmclient;

import kr.co.won.inflearnspringaistudy.exception.CommonError;
import kr.co.won.inflearnspringaistudy.exception.ErrorTypeException;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 동일한 Interface를 가진 경우 Spring에서는 List 형태로 해당 객체들을 가져올 수 있다.
 * => 가져온 객체를 Map 형태로 만들어서 특정 객체에 대해서 가져와서 사용할 수있다.
 * <p>
 * 다형성
 * 다형성을 통해서 원하는 형태의 구현체를 가져와서 사용할 수 있다.
 * 유연성 및 확장성
 * 다형성으로 인해서 다양한 확장이 가능해지기 때문에 유연성 및 확장성이 증가하게 된다.
 * <p>
 * 느슨한 결합
 * 인터페이스 타입으로 받아서 사용하기 때문에 느슨한 결합이 된다.
 * <p>
 * 인터페이스에서는 Logging을 직접적으로 할 수 없기 때문에 Logging에 대한 함수를 만들어서 사용하는 방법과 추상 클래스로 만들어서 처리를 하는 방법이 있다.
 */
public interface LlmWebClientService {

    /**
     * Exception을 처리하기 위한 함수
     *
     * @param chatRequestDto
     * @return
     */
    default Mono<LlmChatResponseDto> getChatChatCompletionWithCatchException(LlmChatRequestDto chatRequestDto) {
        return getChatCompletion(chatRequestDto)
                .onErrorResume(exception -> {
                    if (exception instanceof ErrorTypeException errorTypeException) {
                        CommonError commonError = new CommonError(errorTypeException.getErrorType().getCode(), errorTypeException.getMessage());
                        return Mono.just(new LlmChatResponseDto(commonError, errorTypeException));
                    } else {
                        CommonError commonError = new CommonError(500, exception.getMessage());
                        return Mono.just(new LlmChatResponseDto(commonError, exception));
                    }
                });
    }

    /**
     * LLM에 요청을 보내서 응답을 가져오는 기능
     *
     * @param requestDto
     * @return
     */
    Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto requestDto);

    /**
     * 사용할 Service에 대한 타입을 가져와서 특정 Service를 실행하기 위해서 구분이 되는 값을 가져오기 위한 함수
     *
     * @return
     */
    LlmType getLlmType();

    /**
     * LLM에 대한 응답을 Stream 형태로 전달하는 함수
     *
     * @param llmChatRequestDto
     * @return
     */
    Flux<LlmChatResponseDto> getChatCompletionStream(LlmChatRequestDto llmChatRequestDto);
}
