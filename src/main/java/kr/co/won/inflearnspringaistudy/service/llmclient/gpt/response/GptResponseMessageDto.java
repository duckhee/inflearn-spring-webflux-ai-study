package kr.co.won.inflearnspringaistudy.service.llmclient.gpt.response;

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
public class GptResponseMessageDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -1221229354663950174L;

    /// 응답에 대한 값을 담고 있는 변수
    private String content;

}
