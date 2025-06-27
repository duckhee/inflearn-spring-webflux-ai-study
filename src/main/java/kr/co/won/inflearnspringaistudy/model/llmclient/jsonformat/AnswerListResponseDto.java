package kr.co.won.inflearnspringaistudy.model.llmclient.jsonformat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerListResponseDto implements Serializable {

    private List<String> answerList;


    @Override
    public String toString() {
        return String.join("\n\n", answerList);
    }
}
