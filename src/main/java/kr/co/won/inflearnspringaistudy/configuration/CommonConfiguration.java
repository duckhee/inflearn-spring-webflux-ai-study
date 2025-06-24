package kr.co.won.inflearnspringaistudy.configuration;

import kr.co.won.inflearnspringaistudy.model.llmclient.LlmType;
import kr.co.won.inflearnspringaistudy.service.llmclient.LlmWebClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class CommonConfiguration {

    /**
     * LlmWebClientService에 대한 선택을 하는 Map 타입의 Bean
     *
     * @param llmWebClientServices
     * @return
     */
    @Bean
    public Map<LlmType, LlmWebClientService> getLlmWebClientServiceDelegator(List<LlmWebClientService> llmWebClientServices) {
        return llmWebClientServices
                .stream()
                .collect(Collectors.toMap(LlmWebClientService::getLlmType, Function.identity()));
    }
}
