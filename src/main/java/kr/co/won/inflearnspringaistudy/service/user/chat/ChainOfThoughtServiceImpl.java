package kr.co.won.inflearnspringaistudy.service.user.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.won.inflearnspringaistudy.exception.CustomErrorType;
import kr.co.won.inflearnspringaistudy.exception.ErrorTypeException;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmChatResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmModel;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmType;
import kr.co.won.inflearnspringaistudy.model.llmclient.jsonformat.AnswerListResponseDto;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatRequestDto;
import kr.co.won.inflearnspringaistudy.model.user.chat.UserChatResponseDto;
import kr.co.won.inflearnspringaistudy.service.llmclient.LlmWebClientService;
import kr.co.won.inflearnspringaistudy.util.ChatUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChainOfThoughtServiceImpl implements ChainOfThoughtService {

    private final Map<LlmType, LlmWebClientService> llmWebClientServiceDelegator;

    private final ObjectMapper objectMapper;

    /**
     * 1 단계 -> 문제를 이해하기
     * 2 단계 -> 문제를 단계 별로 풀어가기
     * 3 단계 -> 최종 응답
     * => 해당 단계로 푸는 것은 범용성이 떨어진다.
     * <p>
     * 1. 사용자의 요청을 효율적으로 분석하기 위한 단계를 LLM에게 물어본다.
     * => answerList는 분석 단계를 LLM이 응답을 해준다.
     * 2. 분석 단계 별로 LLM에게 요청을 보내서 상세하게 분석한다.
     * <p>
     * 3. 단계별로 분석된 결과를 종합해서 최종 응답을 보낸다.
     * <p>
     * 방출을 해야 하는 부분이 많을 경우 Sink를 이용하면 손쉽게 방출에 대해서 제어가 가능하다.
     */

    @Override
    public Flux<UserChatResponseDto> getChainOfThoughtResponse(UserChatRequestDto userChatRequestDto) {
        // 방출을 해야 하는 부분이 많을 경우 Sink를 이용하면 손쉽게 방출에 대해서 제어가 가능하다.
        return Flux.create(sink -> {
            String userRequest = userChatRequestDto.getRequest();
            LlmModel requestModel = userChatRequestDto.getLlmModel();

            String establishingThoughtChainPrompt = String.format("""
                    다음은 사용자의 입력입니다. "%s"
                    사용자에게 체계적으로 답변하기 위해서 어떤 단계들이 필요할지 정의해주세요.
                    """, userRequest);

            ///  system에 대한 요청 Prompt JSON 형식을 사용할 때에의 형태이다.
            String establishingThoughtChainSystemPrompt = String.format("""
                    아래처럼 List<String> answerList의 형태를 가지는 JSON FORMAT으로 응답해주세요. 
                    <JSONSCHEMA>
                    {
                        "answerList" : ["", ...]
                    }
                    </JSONSCHEMA>
                    """);

            // LLM으로 요청을 보내는 Dto
            LlmChatRequestDto startChainOfThoughtPrompt = new LlmChatRequestDto(establishingThoughtChainPrompt, establishingThoughtChainSystemPrompt, true, requestModel);
            LlmWebClientService llmWebClientService = llmWebClientServiceDelegator.get(requestModel.getType());
            // 분석 단계에 대한 LLM 요청으로 값 -> 단계 값 가져오기
            Mono<AnswerListResponseDto> cotStepListMono = llmWebClientService.getChatCompletion(startChainOfThoughtPrompt)
                    .map(response -> {
                        String llmResponse = response.getLlmResponse();
                        String extractJsonString = ChatUtils.extractJsonString(llmResponse);
                        try {
                            AnswerListResponseDto answerListResponseDto = objectMapper.readValue(extractJsonString, AnswerListResponseDto.class);
//                            // 사용자에게 데이터를 전달 하고 싶을 때 Sink를 이용해서 전달한다.
//                            sink.next(new UserChatResponseDto("필요한 작업 단계 분석", answerListResponseDto.toString()));
                            return answerListResponseDto;
                        } catch (JsonProcessingException e) {
                            throw new ErrorTypeException("[JsonParseError] json parse error. extractJsonString : " + extractJsonString, CustomErrorType.LLM_RESPONSE_JSON_PARSE_ERROR);
                        }
                    })
                    .doOnNext(publishedResponse -> {
                        // 사용자에게 데이터를 전달 하고 싶을 때 Sink를 이용해서 전달한다.
                        sink.next(new UserChatResponseDto("필요한 작업 단계 분석", publishedResponse.toString()));
                    });

            // 응답을 받은 단계를 받아서 Flux 형태로 변환
            Flux<String> cotStepFlux = cotStepListMono.flatMapMany(cotStepList -> Flux.fromIterable(cotStepList.getAnswerList()));

            // 각 단계 별로 LLM에 요청을 보내고 분석이 완료된 값으로 변경 -> flatMapSequential 을 사용해서 응답에 대한 순서를 보장해준다.
            Flux<String> analyedCotStep = cotStepFlux.flatMapSequential(cotStep -> {
                String cotStepRequestPrompt = String.format("""
                        다음은 사용자의 입력입니다 : %s
                        
                        사용자의 요구를 다음 단계에 따라 분석해주세요 : %s
                        """, userRequest, cotStep);
                /// 단계 별 요청을 하는 부분 -> Mono 형태를 반환하기 때문에 중첩 제거가 필요하다.
                return llmWebClientService.getChatChatCompletionWithCatchException(new LlmChatRequestDto(cotStepRequestPrompt, "", false, requestModel))
                        .map(LlmChatResponseDto::getLlmResponse);
            }).doOnNext(publishedResponse -> sink.next(new UserChatResponseDto("단계별 분석", publishedResponse)));

            // 나누어져 있는 Flux에 대한 응답을 묶어 최종 응답을 위한 요청한다.
            Mono<String> finalAnswerMono = analyedCotStep.collectList().flatMap(stepPromptList -> {
                String concatStepPrompt = String.join("\n", stepPromptList);
                // 마지막 최종 응답을 만들기 위한 요청
                String finalAnswerPrompt = String.format("""
                        다음은 사용자의 입력입니다 : %s
                        아래 사항들을 참고, 분석해서 사용자의 입력에 대한 최종 답변을 해주세요 : 
                        %s
                        """, userRequest, concatStepPrompt);
                return llmWebClientService.getChatChatCompletionWithCatchException(new LlmChatRequestDto(finalAnswerPrompt, "", false, requestModel))
                        .map(LlmChatResponseDto::getLlmResponse);
            });
            // 구독을 해야 비로소 흐름이 시작이 된다.
            finalAnswerMono.subscribe(finalAnswer -> {
                sink.next(new UserChatResponseDto("최종 응답", finalAnswer));
                sink.complete();
            }, error -> {
                log.error("[ChainOfThoughtError] cot Response error", error);
                sink.error(error);
            });
        });
    }
}
