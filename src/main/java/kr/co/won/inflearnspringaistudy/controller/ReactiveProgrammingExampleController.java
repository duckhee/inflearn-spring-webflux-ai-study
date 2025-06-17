package kr.co.won.inflearnspringaistudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

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

    // 비동기적으로 데이터를 발행
    @GetMapping(path = "/onenine/flux")
    public Flux<Integer> produceOneToNineFlux() {
        Flux<Integer> publisher = Flux.create(sink -> {
            ///  list에 값을 넣어주는 것처럼 sink에 데이터의 단위로 넣어주면 된다.
            for (int i = 1; i < 10; i++) {
                try {
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
}
