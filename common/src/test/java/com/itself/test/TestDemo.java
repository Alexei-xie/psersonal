package com.itself.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

/**
 * @Author: duJi
 * @Date: 2024-06-17
 **/
public class TestDemo {
    @Test
    public void test(){
        String name = "流动资产：\n" +
                "CURRENT ASSETS";
        System.out.println(extractChineseCharacters(name));
    }

    /**
     * 匹配中文字符以及中文冒号的特殊符号
     * @param text
     * @return
     */
    public static String extractChineseCharacters(String text) {
        StringBuilder chineseChars = new StringBuilder();
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5：]");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            chineseChars.append(matcher.group());
        }
        return chineseChars.toString();
    }
}
