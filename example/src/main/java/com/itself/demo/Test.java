package com.itself.demo;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: duJi
 * @Date: 2024-05-23
 **/
public class Test {
    public static void main(String[] args) {
        String period = 2024 + "-" + String.format("%02d", 4);
        System.out.println(period);
        System.out.println(DateUtil.date());
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
