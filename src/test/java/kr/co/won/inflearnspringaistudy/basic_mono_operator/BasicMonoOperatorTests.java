package kr.co.won.inflearnspringaistudy.basic_mono_operator;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class BasicMonoOperatorTests {

    @Test
    public void monoCreateFromDataTests() {
        // just를 이용한 데이터 흐름 정의
        Mono.just(1)
                .subscribe(data -> System.out.println("Data Just : " + data));

        // empty를 이용한 데이터 흐름 정의
        Mono.empty()
                .subscribe(data -> System.out.println("Data Empty : "));
    }

    @Test
    public void startMonoFromFunctionTests() {
        /**
         * 임시 마이그레이션
         * restTemplate, JPA >> 블로킹이 발생하는 라이브러리를 Mono로 스레드 분리해서 처리할 때 사용이 된다.
         */
        // 동기 로직을 수행하는 것을 변경해서 비동기 형태로 사용할 때 사용이 된다.
        Mono<String> monoFromCallable = Mono.<String>fromCallable(() -> {
            // 로직 실행 후 동기적 객체 반환
            return callRestTemplate("hello");
        }).subscribeOn(Schedulers.boundedElastic());

        // defer는 Mono 객체를 반환한다.
        Mono<String> deferMono = Mono.defer(() -> {
            return callWebClient("hello");
        });
        // subscribe 되는 시점에 defer 안에 있는 Mono가 실행이 되면서 데이터를 생성한다.
        deferMono.subscribe();

        Mono<String> justMono = callWebClient("hello2");
    }

    @Test
    public void deferMonoNecessityTests() {
        /**
         *  데이터에 대한 조합을 비동기적으로 관리를 하고 싶을 때 defer를 이용한다.
         *  해당 흐름을 Mono로 관리하고 싶을 때 사용한다.
         *  데이터를 만들 때 특정 부분에서 blocking 이 발생할 때 Scheduler를 할당해서 안전하게 처리할 수 있다.
         *  */
        Mono<String> defer = Mono.defer(() -> {
            String a = "hello";
            String b = "world";
            String c = "hello";
            Mono<String> stringMono = callWebClient(a + b + c);
            return stringMono;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Mono에서 데이터 방출의 개수가 많아져서 Flux로 변경할 떄에는 flatMapMany를 이용해서 Flux로 변경이 가능하다.
     */
    @Test
    void monoToChangeFluxTests() {
        Mono<Integer> just = Mono.just(1);
        Flux<Integer> integerFlux = just.flatMapMany(data -> Flux.just(data + 1, data, data + 2));
        integerFlux.subscribe(System.out::println);
    }

    public String callRestTemplate(String request) {
        return request + " callRestTemplate Response!";
    }

    public Mono<String> callWebClient(String request) {
        return Mono.just(request + " callWebClient Response!");
    }

}
