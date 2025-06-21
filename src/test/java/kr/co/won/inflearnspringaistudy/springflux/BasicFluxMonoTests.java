package kr.co.won.inflearnspringaistudy.springflux;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BasicFluxMonoTests {

    @Test
    public void testBasicFluxMonoTests() {
        // just를 이용해서 빈 함수로부터 시작하는 방법이다.
        Flux<Integer> flux = Flux.<Integer>just(1, 2, 3, 4, 5)
                .map(x -> x * 2)
                .filter(value -> value % 4 == 0);

        flux
                .subscribe(data -> System.out.println("Flux Data Call : " + data));

        // Mono를 이용한 async
        Mono<Integer> monoInteger = Mono.<Integer>just(1)
                .map(x -> x * 2);
        monoInteger
//                .filter(value -> value % 4 == 0)
                .subscribe(data -> System.out.println("Mono Data Call : " + data));

    }
}
