package kr.co.won.inflearnspringaistudy.model.llmclient.gemini.response;

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
public class GeminiPart implements Serializable {
    @Serial
    private static final long serialVersionUID = -3141772448384464587L;

    /// 응답이 들어오는 변수
    private String text;
}
