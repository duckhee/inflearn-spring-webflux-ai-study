package kr.co.won.inflearnspringaistudy.model.llmclient.gemini.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter

@AllArgsConstructor
public class GeminiGenerationConfigDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -1427697524731531787L;

    /// 응답에 대한 타입을 지정하는 변수
    private String responseMimeType;

    /**
     * Json 타입만 사용하기 때문에 응답 타입을 application/json 형태로 초기 기본 값을 설정하는 생성자
     */
    public GeminiGenerationConfigDto() {
        this.responseMimeType = MediaType.APPLICATION_JSON_VALUE;
    }
}
