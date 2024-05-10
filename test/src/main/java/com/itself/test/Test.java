package com.itself.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author duJi
 * @Date 2023/11/05
 */
public class Test {
    public static void main(String[] args) {
        // 测试用例
        String dateString1 = "2022-02-01";
        String dateString2 = "2022-02-01 12:12:31";

        // 获取开始时间字符串
        String startTimeString1 = getStartTimeString(dateString1);
        String startTimeString2 = getStartTimeString(dateString2);

        // 打印结果
        System.out.println("指定日期的开始时间1：" + startTimeString1);
        System.out.println("指定日期的开始时间2：" + startTimeString2);
    }

    // 获取指定日期的开始时间的String表示
    public static String getStartTimeString(String dateString) {
        LocalDateTime dateTime;
        if (dateString.contains(" ")) {
            // 解析带时间的日期字符串
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime = LocalDateTime.of(LocalDate.parse(dateString, formatter), LocalTime.MIN);
        } else {
            // 解析仅日期的日期字符串
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            dateTime = LocalDateTime.of(date, LocalTime.MAX);
        }
        // 格式化输出开始时间
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(outputFormatter);
    }
}
