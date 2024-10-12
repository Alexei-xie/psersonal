package com.itself.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: duJi
 * @Date: 2024-06-17
 **/
public class TestDemo {
    @Test
    public void test(){
        System.out.println(Integer.parseInt("202412"));



    }
    @Test
    public void test1(){
        String str = "[{\"LIFNR_TO\":\"12222\",\"LIFNR_FROM\":\"10000\"},{\"LIFNR_TO\":\"12222\",\"LIFNR_FROM\":\"10000\"}]";
        String paramBody = "{\n" +
                "    \"IS_INPUT\": {\n" +
                "        \"MANDT\": \"" + 2 + "\",\n" +
                "        \"BUKRS\": \"" + 2 + "\",\n" +
                "        \"RYEAR\": \"" + 2 + "\",\n" +
                "        \"ZPERIOD\": \"" + 2 + "\",\n" +
                "        \"ITEM2\": {\n" +
                "            \"item\": "+str+"\n" +
                "        }\n" +
                "    },\n" +
                "    \"OT_OUTPUT\":\"\"\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(paramBody);
        System.out.println(String.valueOf(jsonObject.get("ITEM2")));
        System.out.println();
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
