package com.itself.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * 脱敏信息工具类
 * @Author xxw
 * @Date 2021/09/18
 */
public class DesensitizationUtils {

    /**
     * 对手机号进行脱敏处理   例如：188****1234
     * @param phoneNo
     * @return
     */
    public static String encryptMobile(String phoneNo) {
        if (phoneNo == null) {
            return "";
        }
        return replaceBetween(phoneNo, 3, phoneNo.length() - 4, null);
    }



    /**
     * 将字符串开始位置到结束位置之间的字符用指定字符替换
     * @param sourceStr 待处理字符串
     * @param begin	开始位置
     * @param end	结束位置
     * @param replacement 替换字符
     * @return
     */
    private static String replaceBetween(String sourceStr, int begin, int end, String replacement) {
        if (sourceStr == null) {
            return "";
        }
        if (replacement == null) {
            replacement = "*";
        }
        int replaceLength = end - begin;
        if (StringUtils.isNotBlank(sourceStr) && replaceLength > 0) {
            StringBuilder sb = new StringBuilder(sourceStr);
            sb.replace(begin, end, StringUtils.repeat(replacement, replaceLength));
            return sb.toString();
        } else {
            return sourceStr;
        }
    }

}
