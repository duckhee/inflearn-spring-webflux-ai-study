package kr.co.won.inflearnspringaistudy.service.facade;

import kr.co.won.inflearnspringaistudy.model.facade.FacadeHomeResponseDto;
import reactor.core.publisher.Mono;

public interface FacadeService {

    Mono<FacadeHomeResponseDto> getFacadeHomeResponseDto();
}
