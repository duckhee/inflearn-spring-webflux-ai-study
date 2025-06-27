package kr.co.won.inflearnspringaistudy.model.facade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacadeAvailableModel {

//    @JsonProperty 를 이용해서 응답에 대한 필드 명에 대해 변경해서 전달하기도 한다.
    private String displayName;

    private String codeName;
}
