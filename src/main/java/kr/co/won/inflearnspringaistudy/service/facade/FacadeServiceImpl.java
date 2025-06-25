package kr.co.won.inflearnspringaistudy.service.facade;

import kr.co.won.inflearnspringaistudy.model.facade.FacadeAvailableModel;
import kr.co.won.inflearnspringaistudy.model.facade.FacadeHomeResponseDto;
import kr.co.won.inflearnspringaistudy.model.llmclient.LlmModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacadeServiceImpl implements FacadeService {

    @Override
    public Mono<FacadeHomeResponseDto> getFacadeHomeResponseDto() {
        /** 반복문을 이용한 방법 */
//        return Mono.fromCallable(() -> {
//            LlmModel[] values = LlmModel.values();
//            List<FacadeAvailableModel> availableModelList = new ArrayList<>();
//
//            for (int i = 0; i < values.length; i++) {
//                LlmModel availableModel = values[i];
//                FacadeAvailableModel facadeAvailableModel = new FacadeAvailableModel(availableModel.name(), availableModel.getCode());
//                availableModelList.add(facadeAvailableModel);
//            }
//            return new FacadeHomeResponseDto(availableModelList);
//        });
        return Mono.fromCallable(() -> {
            List<FacadeAvailableModel> availableModelList = Arrays.stream(LlmModel.values())
                    .map(availableModel -> new FacadeAvailableModel(availableModel.name(), availableModel.getCode()))
                    .collect(Collectors.toList());
            return new FacadeHomeResponseDto(availableModelList);
        });
    }
}
