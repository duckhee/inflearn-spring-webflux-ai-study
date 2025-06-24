package kr.co.won.inflearnspringaistudy.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    /**
     * WebClient에 대한 Bean 등록 하는 함수
     *
     * @param webClientBuilder - WebClient.Builder는 ComponentBean에 등록이 되어 있기 때문에 주입 받아서 사용할 수 있다.
     * @return
     */
    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }
}
