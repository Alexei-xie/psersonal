package com.itself.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *  https://cloud.tencent.com/developer/article/1665775  (Java中各种时间类型相互转换)
 *
 * 时间日期工具类
 *  String类型格式：2022-11-14
 *  LocalDate类型格式：2022-11-14
 *  Date类型格式：Mon Nov 14 00:00:00 CST 2022
 *  LocalDateTime类型格式：2022-11-14T00:00
 * @Author xxw
 * @Date 2022/10/14
 */
public class TimeUtils {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DAY_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("MMdd");
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * Long类型的日期转为指定格式的字符串类型
     */
    public static String timeMillisToStr(Long dateTime){
        SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
        return sdf.format(new Date(dateTime));
    }



    /**
     * 字符串类型日期转为 LocalDate类型
     */
    public static LocalDate strToLocalDate(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DAY_FORMAT));
    }

    /**
     * LocalDate类型转为Date类型
     */
    public static Date localDateToDate(LocalDate now){
        return Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate类型转为LocalDateTime类型
     */
    public static LocalDateTime localDateToLocalDateTime(LocalDate date){
        return date.atStartOfDay();
    }

    /**
     *  根据周期单位和周期时间量从指定日期开始计算
     * @param date 指定日期 LocalDate类型
     * @param cycle 周期量
     * @param cycleUnit 周期单位
     */
    public static LocalDate getNextDate(LocalDate date,int cycle,String cycleUnit){
        switch (cycleUnit){
            case "day":
                return date.plusDays(cycle);
            case "week" :
                return date.plusWeeks(cycle);
            case "month":
                return date.plusMonths(cycle);
            case "year":
                return date.plusYears(cycle);
            default:
                throw new RuntimeException();
        }
    }

    /**
     * 根据字符串时间单位转换为Calendar日期计算类型
     */
    public static Integer translateTimeUnit(String timeUnit){
        switch (timeUnit){
            case "day":
                return Calendar.DATE;
            case "month":
                return Calendar.MONTH;
            case "year":
                return Calendar.YEAR;
            default:
                throw new RuntimeException();
        }
    }

    /**
     * 根据新旧日期字符串计算两者所差年份
     * @param old 旧日期 2023-03-30
     * @param now 最新的日期  2020-03-30
     * @return  不满一年算一年，一年多算两年
     */
    public static Long computeYear(String old,String now){
        try {
            Date oldDate = sdf.parse(old);
            Date nowDate = sdf.parse(now);
            long a = Long.parseLong(sdf.format(nowDate))-Long.parseLong(sdf.format(oldDate));
            if (equalsLarge(old,now)){
                a= a+1;
            }
            return a;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 比较日期大小，用来判断是否需要对年份进行+1
     */
    private static Boolean equalsLarge(String old,String now){
        try {
            return Long.parseLong(sdf2.format(sdf3.parse(now))) > Long.parseLong(sdf2.format(sdf3.parse(old)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 线程睡眠
     */
    public static void sleep(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TimeUtilDemo{
    public static void main(String[] args) {
        /*LocalDate now = LocalDate.now();
        System.out.println(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        LocalDate date = getNextDate(now, 1, "month");
        System.out.println(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));*/
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1); //此时如果1是负数的时候即(-1)： 日期往前移一天
        System.out.println(calendar.getTime());
        Long s = timeMillisToStr(new Date().getTime());
        System.out.println(s.equals(20221014L));
    }
    public static Long timeMillisToStr(Long dateTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date(dateTime));
        String[] split = format.split("-");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            stringBuffer.append(split[i]);
        }
        return Long.valueOf(stringBuffer.toString());
    }
    /**
     * @param date 当前日期
     * @param cycle 周期量
     * @param cycleUnit 周期单位
     */
    public static LocalDate getNextDate(LocalDate date,int cycle,String cycleUnit){
        switch (cycleUnit){
            case "day":
                return date.plusDays(cycle);
            case "week" :
                return date.plusWeeks(cycle);
            case "month":
                return date.plusMonths(cycle);
            case "year":
                return date.plusYears(cycle);
            default:
                throw new RuntimeException();
        }
    }

}
