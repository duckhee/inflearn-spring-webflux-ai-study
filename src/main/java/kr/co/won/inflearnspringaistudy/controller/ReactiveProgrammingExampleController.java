package kr.co.won.inflearnspringaistudy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/reactive")
public class ReactiveProgrammingExampleController {

    // 동기적으로 데이터를 처리를 한다.
    // 1~9 까지 출력 하는 API
    @GetMapping(path = "/onenine/list")
    public List<Integer> produceOneToNine() {
        List<Integer> sink = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            /// 0.5초간 Delay 발생을 시키기 위한 코드
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            sink.add(i);
        }
        return sink;
    }

    /**
     * 동기 객체를 반환할 때 fromCallable을 이용해서 비동기로 변환해서 비동기 함수로 변경이 가능하다.
     */
    @GetMapping(path = "/onenine/list/v1")
    public Mono<List<Integer>> produceOneToNineV1() {
        Mono<List<Integer>> fromCallableMono = Mono.fromCallable(() -> {
            List<Integer> sink = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                /// 0.5초간 Delay 발생을 시키기 위한 코드
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                sink.add(i);
            }
            return sink;
        }).subscribeOn(Schedulers.boundedElastic());
        return fromCallableMono;
    }

    /**
     * defer를 이용해서 동기적 실행을 호출이 된 시점으로 변경해서 LazyLoading 형태로 동작한다.
     */
    @GetMapping(path = "/onenine/list/v2")
    public Mono<List<Integer>> produceOneToNineV2() {
        Mono<List<Integer>> listMono = Mono.defer(() -> {
            List<Integer> sink = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                /// 0.5초간 Delay 발생을 시키기 위한 코드
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                sink.add(i);
            }
            return Mono.just(sink);
        }).subscribeOn(Schedulers.boundedElastic());
        return listMono;
    }


    // 비동기적으로 데이터를 발행
    @GetMapping(path = "/onenine/flux")
    public Flux<Integer> produceOneToNineFlux() {
        Flux<Integer> publisher = Flux.create(sink -> {
            ///  list에 값을 넣어주는 것처럼 sink에 데이터의 단위로 넣어주면 된다.
            for (int i = 1; i < 10; i++) {
                try {
                    log.info("현재 실행 중인 Thread : {}", Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                // sink에 데이터를 담아 주는 것
                sink.next(i);
            }
            /// 데이터에 대한 전송을 완료 했다고 알려주기 위한 함수
            sink.complete();
        });
        return publisher;
    }


    // 비동기적으로 데이터를 발행
    @GetMapping(path = "/onenine/flux-scheduler")
    public Flux<Integer> produceOneToNineFluxScheduler() {
        Flux<Integer> publisher = Flux.<Integer>create(sink -> {
            ///  list에 값을 넣어주는 것처럼 sink에 데이터의 단위로 넣어주면 된다.
            for (int i = 1; i < 10; i++) {
                try {
                    log.info("현재 실행 중인 Thread : {}", Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                // sink에 데이터를 담아 주는 것
                sink.next(i);
            }
            /// 데이터에 대한 전송을 완료 했다고 알려주기 위한 함수
            sink.complete();
        }).subscribeOn(Schedulers.boundedElastic()); // scheduler를 할당 해주면 event-loop thread는 바로 반환이 되고 실행 로직은 scheduler에 의해 생성된 thread에게 위임이 된다.
        return publisher;
    }
}
