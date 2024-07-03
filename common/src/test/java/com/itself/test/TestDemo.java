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
        // String name = "流动资产：\n" +
        //         "CURRENT ASSETS";
        // System.out.println(extractChineseCharacters(name));
        String date = "WMP240502085226810622、WMP240324102938378039、WMP240223160253304704、WMP231119184809258983、WMP231025063129781501、WMP231007140200614105、WMP230830101252220627、WMP230808111655175857、WMP230802101627231475、WMP230801182421284214、WMP230711110537396093、WMP230628131431476590、WMP230614140028245068、WMP230610133844396540、WMP230606200939382028、WMP230606200411146207、WMP230405125134757411、WMP230223";
        System.out.println(date.length());
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

    /**
     * 提取年和月
     * @param text
     * @return
     */
    public static String[] extractYearAndMonth(String text) {
        String[] yearAndMonth = new String[2];
        // 正则表达式匹配年和月
        Pattern pattern = Pattern.compile("(\\d{4})年(\\d{1,2})月");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            yearAndMonth[0] = matcher.group(1); // 提取年份
            yearAndMonth[1] = matcher.group(2); // 提取月份
        }
        return yearAndMonth;
    }
}
