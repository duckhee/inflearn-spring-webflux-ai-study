package kr.co.won.inflearnspringaistudy.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("LLM AI Chat Service")
                                .version("1.0")
                                .description("LLM AI Example")
                                .license(new License().name("Apache 2.0"))
                );
    }
}
