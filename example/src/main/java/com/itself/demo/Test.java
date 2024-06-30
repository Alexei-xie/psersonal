package com.itself.demo;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: duJi
 * @Date: 2024-05-23
 **/
public class Test {
    public static void main(String[] args) {
        List<String> list = List.of("配套a1", "配套b2", "配套v3", "配套d4", "配套e5");
        System.out.println(list.contains("配套a1"));
    }
    private static Integer handleStageNo(String stageName) {
        if (StringUtils.isBlank(stageName)) {
            return null;
        }
        Pattern pattern = Pattern.compile("配套(\\d+)");
        Matcher matcher = pattern.matcher(stageName);
        String result = "";
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return Integer.parseInt(result);
    }
}
