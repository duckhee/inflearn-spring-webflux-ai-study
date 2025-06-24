package kr.co.won.inflearnspringaistudy.model.llmclient.gpt.request;

import kr.co.won.inflearnspringaistudy.model.llmclient.gpt.GptMessageRole;
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
public class GptCompletionRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -5429233824818338595L;

    /// 채팅에 대한 내용을 담는 변수
    private String content;

    /// 현재 내용이 누가 입력을 한 것인지 확인하기 위한 변수
    private GptMessageRole role;
}
