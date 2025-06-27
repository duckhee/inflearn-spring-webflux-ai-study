package kr.co.won.inflearnspringaistudy.basic_flux_mono_error_and_signal;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class FluxMonoErrorAndSignalTests {

    @Test
    void basicSignalTests() {

        Flux.range(1, 4)
                .doOnNext(data -> System.out.println("doOnNext = " + data))
                .doOnComplete(() -> System.out.println("doOnComplete"))
                .doOnError(throwable -> System.out.println("doOnError" + throwable))
                .subscribe(data -> System.out.println("signal test : " + data));
    }

    @Test
    void errorSignalTests() {
        // 비동기 객체에서 Exception이 발생하는 경우 내부 흐름에서 자체적으로 에러를 받아서 처리하기 때문에 Event-Loop Thread로 에러가 전달 되지 않는다.
        try {
            Flux.range(1, 10)
                    .map(data -> {
                        if (data == 3) {
                            throw new RuntimeException();
                        }
                        return data * 2;
                    })
//                    .onErrorMap(throwable -> new RuntimeException(throwable))
                    .onErrorReturn(10)
//                    .onErrorComplete()
                    .subscribe(data -> System.out.println("signal test : " + data));
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }

    /**
     * Flux, Mono .error()
     */
    @Test
    void fluxMonoDotErrorTests() {
        Flux.just(1, 2, 3, 4)
                .flatMap(data -> {
                    if (data != 3) {
                        return Mono.just(data);
                    } else {
                        return Mono.error(new RuntimeException());
//                        throw new RuntimeException();
                    }
                })
                .subscribe(data -> System.out.println("signal test : " + data));
    }
}
