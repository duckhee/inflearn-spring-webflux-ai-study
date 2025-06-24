package kr.co.won.inflearnspringaistudy.service.llmclient.gpt.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GptResponseFormat implements Serializable {
    @Serial
    private static final long serialVersionUID = -3886746559747425983L;

    /// 응답에 대한 타입 정의 변수
    private String type;
}
