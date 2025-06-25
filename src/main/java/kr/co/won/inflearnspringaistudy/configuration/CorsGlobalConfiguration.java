package kr.co.won.inflearnspringaistudy.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * WebFluxConfigurer는 웹 서버에 대한 보안을 위해서 사용하는 보안 관련 기능을 구현할 때 사용이 된다.
 */
@Configuration
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    /**
     * 신뢰할 수 있는 webServer에 대한 추가 설정이 가능하게 해주는 함수이다.
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 경로에 대한 설정
                .allowedOrigins("*") // 요청한 서버에 대한 아이피에 대한 정보 설정
                .allowedHeaders("*") // 요청한 헤더에 대한 정보 설정
                .allowedMethods("*") // 요청할 HTTP 요청 Method에 대한 설정
                .maxAge(3600); // 요청 시 preflight 세션에 대한 유지 시간
    }
}
