package kr.co.won.inflearnspringaistudy.controller.facade;

import kr.co.won.inflearnspringaistudy.model.facade.FacadeHomeResponseDto;
import kr.co.won.inflearnspringaistudy.service.facade.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/facade")
@RequiredArgsConstructor
public class FacadeController {

    private final FacadeService facadeService;


    @PostMapping(path = "/home")
    public Mono<FacadeHomeResponseDto> homeFacade() {
        return facadeService.getFacadeHomeResponseDto();
    }

}
