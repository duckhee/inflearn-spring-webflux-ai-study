package kr.co.won.inflearnspringaistudy.model.llmclient.gpt.response;

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
public class GptChoice implements Serializable {

    @Serial
    private static final long serialVersionUID = 5964743287572816409L;

    /// 응답이 어떤 이유로 끝났는지 알려주는 값이다.
    private String finishReason;

    ///  응답에 대한 값을 담고 있는 변수
    private GptResponseMessageDto message;
}
