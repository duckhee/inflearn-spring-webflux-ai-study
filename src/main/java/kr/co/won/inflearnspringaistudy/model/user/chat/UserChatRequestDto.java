package kr.co.won.inflearnspringaistudy.model.user.chat;

import kr.co.won.inflearnspringaistudy.model.llmclient.LlmModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserChatRequestDto implements Serializable {


    @Serial
    private static final long serialVersionUID = -5642288840309319412L;

    private String request;

    private LlmModel llmModel;
}
