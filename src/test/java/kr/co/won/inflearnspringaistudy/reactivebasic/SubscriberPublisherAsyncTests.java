package kr.co.won.inflearnspringaistudy.reactivebasic;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

//@SpringBootTest
public class SubscriberPublisherAsyncTests {


    @Test
    public void publisherAsyncOneToNineTest() {
        /// Scheduler를 할당할 때에는 명시적으로 반환 타입을 지정해주는 것이 좋다.
        Flux<Integer> integerFlux = Flux
//                .<Integer>fromIterable(IntStream.rangeClosed(0, 9).boxed().toList())
                .<Integer>create(sink -> {
                    for(int i = 1; i <=9; i++){
                        try{
                            Thread.sleep(500);
                        }catch (InterruptedException e){

                        }
                        sink.next(i);
                    }
                    sink.complete();
                })
                .map(x -> x * 4)
                .filter(x -> x % 4 == 0)
                .subscribeOn(Schedulers.boundedElastic());
        // 구독
        integerFlux.subscribe((data)->{
            System.out.println("Current Thread : " + Thread.currentThread().getName());
            System.out.println("data = " + data);
        });
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Completed");

    }


}
