package kr.co.won.inflearnspringaistudy.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatUtils {

    /**
     * 응답에 대한 값을 {} 안에 있는 문자열을 가져오기 위한 Utility Method
     *
     * @param context
     * @return
     */
    public static String extractJsonString(String context) {
        int startIdx = context.indexOf("{");
        // 마지막 괄호 위치
        int endIdx = context.lastIndexOf("}");
        if (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
            return context.substring(startIdx, endIdx + 1);
        }
        log.error("extractJsonString error" + context);
        return null;
    }
}
