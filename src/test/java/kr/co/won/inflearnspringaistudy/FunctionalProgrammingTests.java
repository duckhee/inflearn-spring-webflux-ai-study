package kr.co.won.inflearnspringaistudy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@SpringBootTest
public class FunctionalProgrammingTests {

    @Test
    public void produceOneToNine() {
        List<Integer> sink = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            sink.add(i);
        }

        // * 2
        sink = mappingInteger(sink, (data)-> data *2);

        // 4의 배수만 남기기
        sink = filtering(sink, (data)-> data % 4 == 0);

        printForEach(sink, (data)-> System.out.println(data));

    }

    @Test
    void javaStreamTests(){
        List<Integer> sink = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            sink.add(i);
        }

//        sink.stream()
                IntStream.rangeClosed(1, 9).boxed()
                .map( data -> data * 2)
                .filter(d -> d % 4 == 0)
                .forEach(System.out::println);
    }

    @Test
    void reactiveTests(){
        Flux<Integer> publisher = Flux.create(sink -> {
            ///  list에 값을 넣어주는 것처럼 sink에 데이터의 단위로 넣어주면 된다.
            for (int i = 1; i < 10; i++) {
                // sink에 데이터를 담아 주는 것
                sink.next(i);
            }
            /// 데이터에 대한 전송을 완료 했다고 알려주기 위한 함수
            sink.complete();
        });
        publisher.subscribe(data -> System.out.println("web flux : "+data));
    }

    @Test
    void reactiveOperatorTests(){
        Flux<Integer> integerFlux = Flux.fromIterable(
                IntStream
                        .rangeClosed(1, 9)
                        .boxed()
                        .map(data -> data * 4)
                        .filter(data -> data % 4 == 0)
                        .toList());
        integerFlux.subscribe(System.out::println);
    }

    private static void printForEach(List<Integer> sink, Consumer<Integer> consumer) {
        for(int i = 0; i < sink.size(); i++){
            consumer.accept(sink.get(i));
        }
    }

    private static List<Integer> filtering(List<Integer> sink, Function<Integer,Boolean> filterFunction) {
        sink = sink.stream().filter(value -> filterFunction.apply(value)).collect(Collectors.toList());
        return sink;
    }

    private static List<Integer> mappingInteger(List<Integer> sink, Function<Integer, Integer> mapper) {
        List<Integer> sink1 = new ArrayList<>();
        for(int i = 0; i <=8 ;i++ ){

            sink1.add(mapper.apply(sink.get(i)));
        }
        sink = sink1;
        return sink;
    }
}
