package kr.co.won.inflearnspringaistudy.basic_flatmap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

//@SpringBootTest
public class FlatMapOperatorTests {

    /**
     * Mono<Mono<T>> -> Mono<<T>
     * Mono<Flux<T>> -> Flux<T>
     * Flux<Mono<T>> -> Mono<T>
     */

    @Test
    void monoFluxFlatMapTests() {
        Mono<Integer> one = Mono.just(1);
        // 평탄화를 통해서 흐름에 대한 명확하게 제어
        Flux<Integer> integerFlux = one.flatMapMany(data -> {
            return Flux.just(data, data + 1, data + 2);
        });

        // 비동기 객체 안에 비동기 객체가 있는 경우
        Mono<Flux<Integer>> map = one.map(data -> {
            return Flux.just(data, data + 1, data + 2);
        });
        integerFlux.subscribe(System.out::println);
    }

    @Test
    void testWebClientFlatMap() {
        Flux<Mono<String>> just = Flux.just(
                callWebClient("1step", 1500),
                callWebClient("2step", 1000),
                callWebClient("3step", 500)
        );

        Flux<Mono<String>> monoFlux = Flux.<Mono<String>>create(sink -> {
            sink.next(callWebClient("1step", 1500));
            sink.next(callWebClient("2step", 1000));
            sink.next(callWebClient("3step", 500));
            sink.complete();
        });

        // flatMap을 이용해서 Mono형태로 변경
        Flux<String> stringFlux = just.flatMap(data -> data);
        Flux<String> stringFlux1 = monoFlux.flatMap(data -> data);
        // 확인
        stringFlux.subscribe(System.out::println);
        stringFlux1.subscribe(System.out::println);

        // 확인을 위한 대기
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 입역된 순서대로 보장하고 싶을 때에는 FlatMapSequential 을 이용한다.
    @Test
    void flatMapSequentialTests() {
        Flux<Mono<String>> just = Flux.just(
                callWebClient("1step", 1500),
                callWebClient("2step", 1000),
                callWebClient("3step", 500)
        );

        Flux<Mono<String>> monoFlux = Flux.<Mono<String>>create(sink -> {
            sink.next(callWebClient("1step", 1500));
            sink.next(callWebClient("2step", 1000));
            sink.next(callWebClient("3step", 500));
            sink.complete();
        });

        // flatMap을 이용해서 Mono형태로 변경 -> 입력 순서에 대한 보장하기 위해서는 flatMapSequential 을 사용해야 한다.
        Flux<String> stringFlux = just.flatMapSequential(data -> data);
        Flux<String> stringFlux1 = monoFlux.flatMapSequential(data -> data);
        // 확인
        stringFlux.subscribe(System.out::println);
        stringFlux1.subscribe(System.out::println);

        // 확인을 위한 대기
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void mergeTests() {
        Flux<String> just = Flux.merge(
                callWebClient("1step", 1500),
                callWebClient("2step", 1000),
                callWebClient("3step", 500)
        );
        Flux<String> mergeSequential = Flux.mergeSequential(
                callWebClient("1step", 1500),
                callWebClient("2step", 1000),
                callWebClient("3step", 500)
        );
        // 확인
        just.subscribe(System.out::println);
        mergeSequential.subscribe(System.out::println);

        // 확인을 위한 대기
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Mono<String> callWebClient(String request, long delay) {
        return Mono.defer(() -> {
            try {
                Thread.sleep(delay);
                return Mono.just(request + " -> delay : " + delay);
            } catch (InterruptedException e) {
                return Mono.empty();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
