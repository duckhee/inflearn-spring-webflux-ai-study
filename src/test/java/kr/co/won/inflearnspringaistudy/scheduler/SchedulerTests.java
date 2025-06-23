package kr.co.won.inflearnspringaistudy.scheduler;

import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


public class SchedulerTests {

    @Test
    void schedulerBasicTest() {
        Flux.<Integer>just(1, 2, 3, 4, 5)
                .map(data -> {
                    System.out.println(Thread.currentThread().getName());
                    return data * 2;
                })
                .publishOn(Schedulers.parallel()) // publish에 대한 아래 스트림 부터 스케줄러에 의한 쓰레드에서 처리
                .filter(data -> {
                    System.out.println(Thread.currentThread().getName());
                    return data % 4 == 0;
                })
                .subscribeOn(Schedulers.boundedElastic()) // subscribe에 대한 부분을 스케줄러를 이용한 처리
                .subscribe(data -> System.out.println("basic flux : " + data));

        Mono.<Integer>just(1)
                .map(data -> data * 2)
                .filter(data -> data % 4 == 0)
                .subscribe(data -> System.out.println("basic mono : " + data));


    }
}
