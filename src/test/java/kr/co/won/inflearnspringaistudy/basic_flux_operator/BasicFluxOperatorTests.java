package kr.co.won.inflearnspringaistudy.basic_flux_operator;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class BasicFluxOperatorTests {

    @Test
    void fluxOperatorDataStartTests() {
        // just를 이용한 흐름 시작
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subscribe(System.out::println);

        // from~ 시리즈를 이용한 데이터 흐름 시작
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Flux.fromIterable(integers)
                .subscribe(System.out::println);
    }

    @Test
    void fluxOperatorFunctionStartTests() {
        // defer의 경우 안에서 Flux 객체를 반환해야 한다.
        Flux.defer(() -> {
                    return Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                })
                .subscribe(System.out::println);

        // create를 이용한 Flux 객체 생성
        Flux.create(sink -> {
            sink.next(1);
            sink.next(2);
            sink.next(3);
            sink.next(4);
            sink.next(5);
            // 완료가 되었다는 것을 알려준다.
            sink.complete();
        }).subscribe(System.out::println);

    }

    @Test
    void fluxCreateOperatorTests() {
        // create 를 이용한 제어 부분 -> 재귀함수에 대한 처리
        Flux.<String>create(sink -> {
            AtomicInteger counter = new AtomicInteger(0);
            recursiveFunction(sink, counter);
        }).subscribe(System.out::println);
    }


    @Test
    void fluxCreateOperatorWithContextTests() {
        // create 를 이용한 제어 부분 -> 재귀함수에 대한 처리
        Flux.<String>create(sink -> {
                    recursiveFunction(sink);
                })
                .contextWrite(Context.of("atomicInteger", new AtomicInteger(0)))
                .subscribe(System.out::println);
    }

    @Test
    void fluxCollectListTests() {
        Mono<List<Integer>> listMono = Flux.<Integer>just(1, 2, 3, 4, 5)
                .map(data -> data * 2)
                .filter(value -> value % 4 == 0)
                .collectList(); // flux에 대한 데이터를 출력을 모두 모아서 Mono 형태로 변환해서 반환해주는 기능이다.
        listMono.subscribe(System.out::println);
    }

    // counter 와 같이 여러 Thread에서 접근이 필요할 때에는 Atomic 타입을 이용하는 것이 좋다.
    public void recursiveFunction(FluxSink<String> sink) {
        AtomicInteger atomicInteger = sink.contextView().get("atomicInteger");
        // 값을 증가 시키고 가져온다.
        if (atomicInteger.incrementAndGet() < 10) {
            sink.next("next count : " + atomicInteger);
            recursiveFunction(sink);
        } else {
            sink.complete();
        }
    }

    // counter 와 같이 여러 Thread에서 접근이 필요할 때에는 Atomic 타입을 이용하는 것이 좋다.
    public void recursiveFunction(FluxSink<String> sink, AtomicInteger atomicInteger) {
        // 값을 증가 시키고 가져온다.
        if (atomicInteger.incrementAndGet() < 10) {
            sink.next("next count : " + atomicInteger);
            recursiveFunction(sink, atomicInteger);
        } else {
            sink.complete();
        }
    }
}
