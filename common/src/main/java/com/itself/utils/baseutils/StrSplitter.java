package com.itself.utils.baseutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: duJi
 * @Date: 2024-05-07
 **/
public class StrSplitter {

    /**
     * 切分字符串为字符串数组
     *
     * @param str 被切分的字符串
     * @param separator 分隔符字符
     * @param limit 限制分片数
     * @param isTrim 是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     * @since 3.0.8
     */
    public static String[] splitToArray(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty){
        return toArray(split(str, separator, limit, isTrim, ignoreEmpty));
    }

    /**
     * 切分字符串，不忽略大小写
     *
     * @param str 被切分的字符串
     * @param separator 分隔符字符串
     * @param limit 限制分片数
     * @param isTrim 是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     * @since 3.0.8
     */
    public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty){
        return split(str, separator, limit, isTrim, ignoreEmpty, false);
    }
    /**
     * 切分字符串
     *
     * @param str 被切分的字符串
     * @param separator 分隔符字符串
     * @param limit 限制分片数
     * @param isTrim 是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @param ignoreCase 是否忽略大小写
     * @return 切分后的集合
     * @since 3.2.1
     */
    public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase){
        if(StrUtil.isEmpty(str)){
            return new ArrayList<String>(0);
        }
        if(limit == 1){
            return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
        }

        if(StrUtil.isEmpty(separator)){//分隔符为空时按照空白符切分
            return split(str, limit);
        }else if(separator.length() == 1){//分隔符只有一个字符长度时按照单分隔符切分
            return split(str, separator.charAt(0), limit, isTrim, ignoreEmpty, ignoreCase);
        }

        final ArrayList<String> list = new ArrayList<>();
        int len = str.length();
        int separatorLen = separator.length();
        int start = 0;
        int i = 0;
        while(i < len){
            i = StrUtil.indexOf(str, separator, start, ignoreCase);
            if(i > -1){
                addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
                start = i + separatorLen;

                //检查是否超出范围（最大允许limit-1个，剩下一个留给末尾字符串）
                if(limit > 0 && list.size() > limit-2){
                    break;
                }
            }else{
                break;
            }
        }
        return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
    }
    /**
     * 切分字符串
     *
     * @param str 被切分的字符串
     * @param separator 分隔符字符
     * @param limit 限制分片数，-1不限制
     * @param isTrim 是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @param ignoreCase 是否忽略大小写
     * @return 切分后的集合
     * @since 3.2.1
     */
    public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase){
        if(StrUtil.isEmpty(str)){
            return new ArrayList<String>(0);
        }
        if(limit == 1){
            return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
        }

        final ArrayList<String> list = new ArrayList<>(limit > 0 ? limit : 16);
        int len = str.length();
        int start = 0;//切分后每个部分的起始
        for(int i = 0; i < len; i++){
            if(charEquals(separator, str.charAt(i), ignoreCase)){
                addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
                start = i+1;//i+1同时将start与i保持一致

                //检查是否超出范围（最大允许limit-1个，剩下一个留给末尾字符串）
                if(limit > 0 && list.size() > limit-2){
                    break;
                }
            }
        }
        return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);//收尾
    }
    /**
     * 使用空白符切分字符串<br>
     * 切分后的字符串两边不包含空白符，空串或空白符串并不做为元素之一
     *
     * @param str 被切分的字符串
     * @param limit 限制分片数
     * @return 切分后的集合
     * @since 3.0.8
     */
    public static List<String> split(String str, int limit){
        if(StrUtil.isEmpty(str)){
            return new ArrayList<String>(0);
        }
        if(limit == 1){
            return addToList(new ArrayList<String>(1), str, true, true);
        }

        final ArrayList<String> list = new ArrayList<>();
        int len = str.length();
        int start = 0;//切分后每个部分的起始
        for(int i = 0; i < len; i++){
            if(isBlankChar(str.charAt(i))){
                addToList(list, str.substring(start, i), true, true);
                start = i+1;//i+1同时将start与i保持一致

                //检查是否超出范围（最大允许limit-1个，剩下一个留给末尾字符串）
                if(limit > 0 && list.size() > limit-2){
                    break;
                }
            }
        }
        return addToList(list, str.substring(start, len), true, true);//收尾
    }


    // -----------------------------------------------------------------------------------------------------------------
    /**
     * List转Array
     * @param list List
     * @return Array
     */
    private static String[] toArray(List<String> list){
        return list.toArray(new String[list.size()]);
    }
    /**
     * 将字符串加入List中
     * @param list 列表
     * @param part 被加入的部分
     * @param isTrim 是否去除两端空白符
     * @param ignoreEmpty 是否略过空字符串（空字符串不做为一个元素）
     * @return 列表
     */
    private static List<String> addToList(List<String> list, String part, boolean isTrim, boolean ignoreEmpty){
        part = part.toString();
        if(isTrim){
            part = part.trim();
        }
        if( !ignoreEmpty || !part.isEmpty()){
            list.add(part);
        }
        return list;
    }
    // -----------------------------------------------------------------------------------------------------------------
    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     * @since 4.0.10
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a'
                || c == '\u0000'
                // issue#I5UGSQ，Hangul Filler
                || c == '\u3164'
                // Braille Pattern Blank
                || c == '\u2800'
                // MONGOLIAN VOWEL SEPARATOR
                || c == '\u180e';
    }

    /**
     * 比较两个字符是否相同
     *
     * @param c1              字符1
     * @param c2              字符2
     * @param caseInsensitive 是否忽略大小写
     * @return 是否相同
     * @since 4.0.3
     */
    public static boolean charEquals(char c1, char c2, boolean caseInsensitive) {
        if (caseInsensitive) {
            return Character.toLowerCase(c1) == Character.toLowerCase(c2);
        }
        return c1 == c2;
    }
}
