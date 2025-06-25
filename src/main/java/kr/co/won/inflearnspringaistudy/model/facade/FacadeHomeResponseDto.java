package kr.co.won.inflearnspringaistudy.model.facade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FacadeHomeResponseDto {

    private List<FacadeAvailableModel> availableModelList;
}
