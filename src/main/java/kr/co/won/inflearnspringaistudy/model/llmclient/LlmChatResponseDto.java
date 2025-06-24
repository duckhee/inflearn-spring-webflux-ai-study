package kr.co.won.inflearnspringaistudy.model.llmclient;

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
public class LlmChatResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -6995650262910361894L;

    private String llmResponse;
}
