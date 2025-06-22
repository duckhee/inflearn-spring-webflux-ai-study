package kr.co.won.inflearnspringaistudy.reactivebasic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


//@SpringBootTest
public class WebClientTests {


    private WebClient webClient = WebClient.builder().build();

    //    @Test
    public void testWebClient() {
        /// Scheduler를 할당할 때에는 명시적으로 반환 타입을 지정해주는 것이 좋다.
        Flux<Integer> integerFlux = webClient.get()
                .uri("http://localhost:8080/reactive/onenine/flux-scheduler")
                .retrieve() // 요청을 발송하고 받아오기 위한 호출
                .bodyToFlux(Integer.class); // 결과 값을 Body로 받아준다.
        // 구독
        integerFlux.subscribe((data) -> {
            System.out.println("Current Thread : " + Thread.currentThread().getName());
            System.out.println("data = " + data);
        });
        System.out.println("completed event-loop thread");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
