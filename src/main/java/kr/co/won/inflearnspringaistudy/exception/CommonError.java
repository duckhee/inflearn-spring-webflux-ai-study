package kr.co.won.inflearnspringaistudy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonError {

    private String errorCode;
    private String errorMessage;

    public CommonError(int errorCode, String errorMessage) {
        this.errorCode = String.valueOf(errorCode);
        this.errorMessage = errorMessage;
    }
}
