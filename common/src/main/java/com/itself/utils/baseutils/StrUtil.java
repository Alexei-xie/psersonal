package com.itself.utils.baseutils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: duJi
 * @Date: 2024-05-07
 **/
public class StrUtil {

    private static final char[] WHITESPACE_CHARS = {' ', '\t', '\n', '\f', '\r'};
    public static final int INDEX_NOT_FOUND = -1;

    public static final String SPACE = " ";
    public static final String TAB = "	";
    public static final String DOT = ".";
    public static final String DOUBLE_DOT = "..";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String EMPTY = "";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UNDERLINE = "_";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String BRACKET_START = "[";
    public static final String BRACKET_END = "]";
    public static final String COLON = ":";

    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp;";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_APOS = "&apos;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";
    public static final String HTML_MDASH = "&mdash;";
    public static final String HTML_NDASH = "&ndash;";
    public static final String HTML_TRADE = "&trade;";
    public static final String HTML_DIVED = "&divide;";
    public static final String HTML_TIMES = "&times;";
    public static final String HTML_MINUS = "&minus;";
    public static final String HTML_RSQUO = "&rsquo;";
    public static final String HTML_LSQUO = "&lsquo;";
    public static final String HTML_GE = "&ge;";
    public static final String HTML_LE = "&le;";
    public static final String HTML_SDOT = "&sdot;";
    public static final String HTML_SIM = "&sim;";

    public static final String EMPTY_JSON = "{}";


    /**
     * 对姓名进行脱敏，保留第一个字符，其余字符替换为 *
     */
    public static String desName(String name){
        name = name.trim();
        if (StringUtils.isBlank(name)){
            return "";
        }
        // 如果姓名长度为1，则不需要脱敏
        if (name.length() == 1) {
            return name;
        }
        // 姓名长度大于2的情况
        StringBuilder desensitizedName = new StringBuilder();
        desensitizedName.append(name.charAt(0)); // 保留第一个字符
        desensitizedName.append("*".repeat(name.length() - 1)); // 将其他字符替换为*
        return desensitizedName.toString();
    }

    /**
     * 获取字符串中对应的数字序号
     * @param stageName 智慧湾1期
     * @return 1
     */
    public static Integer handleStageNo(String stageName){
        if (StringUtils.isBlank(stageName)){
            return null;
        }
        Pattern pattern = Pattern.compile("(\\d+)期");
        Matcher matcher = pattern.matcher(stageName);
        String result = "";
        if (matcher.find()){
            result = matcher.group(1);
        }
        return Integer.parseInt(result);
    }
    /**
     * 字符串是否为非空白 空白的定义如下： <br>
     * 1、不为null <br>
     * 2、不为""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }
    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    /**
     * 替换字符串中的指定字符串
     *
     * @param str 字符串
     * @param searchStr 被查找的字符串
     * @param replacement 被替换的字符串
     * @return 替换后的字符串
     * @since 4.0.3
     */
    public static String replace(CharSequence str, CharSequence searchStr, CharSequence replacement) {
        return replace(str, 0, searchStr, replacement, false);
    }
    /**
     * 替换字符串中的指定字符串
     *
     * @param str 字符串
     * @param fromIndex 开始位置
     * @param searchStr 被查找的字符串
     * @param replacement 被替换的字符串
     * @param ignoreCase 是否忽略大小写
     * @return 替换后的字符串
     * @since 4.0.3
     */
    public static String replace(CharSequence str, int fromIndex, CharSequence searchStr, CharSequence replacement, boolean ignoreCase) {
        if (isEmpty(str) || isEmpty(searchStr)) {
            return str(str);
        }
        if (null == replacement) {
            replacement = EMPTY;
        }

        final int strLength = str.length();
        final int searchStrLength = searchStr.length();
        if (fromIndex > strLength) {
            return str(str);
        } else if (fromIndex < 0) {
            fromIndex = 0;
        }

        final StringBuilder result = new StringBuilder();
        if (0 != fromIndex) {
            result.append(str.subSequence(0, fromIndex));
        }

        int preIndex = fromIndex;
        int index = fromIndex;
        while ((index = indexOf(str, searchStr, preIndex, ignoreCase)) > -1) {
            result.append(str.subSequence(preIndex, index));
            result.append(replacement);
            preIndex = index + searchStrLength;
        }

        if (preIndex < strLength) {
            // 结尾部分
            result.append(str.subSequence(preIndex, strLength));
        }
        return result.toString();
    }
    /**
     * 指定范围内反向查找字符串
     *
     * @param str 字符串
     * @param searchStr 需要查找位置的字符串
     * @param fromIndex 起始位置
     * @param ignoreCase 是否忽略大小写
     * @return 位置
     * @since 3.2.1
     */
    public static int indexOf(final CharSequence str, CharSequence searchStr, int fromIndex, boolean ignoreCase) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }

        final int endLimit = str.length() - searchStr.length() + 1;
        if (fromIndex > endLimit) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return fromIndex;
        }

        if (false == ignoreCase) {
            // 不忽略大小写调用JDK方法
            return str.toString().indexOf(searchStr.toString(), fromIndex);
        }

        for (int i = fromIndex; i < endLimit; i++) {
            if (isSubEquals(str, i, searchStr, 0, searchStr.length(), true)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }
    /**
     * 截取两个字符串的不同部分（长度一致），判断截取的子串是否相同<br>
     * 任意一个字符串为null返回false
     *
     * @param str1 第一个字符串
     * @param start1 第一个字符串开始的位置
     * @param str2 第二个字符串
     * @param start2 第二个字符串开始的位置
     * @param length 截取长度
     * @param ignoreCase 是否忽略大小写
     * @return 子串是否相同
     * @since 3.2.1
     */
    public static boolean isSubEquals(CharSequence str1, int start1, CharSequence str2, int start2, int length, boolean ignoreCase) {
        if (null == str1 || null == str2) {
            return false;
        }

        return str1.toString().regionMatches(ignoreCase, start1, str2.toString(), start2, length);
    }

    /**
     * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     * 例如：HelloWorld=》hello_world
     *
     * @param camelCaseStr 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String toUnderlineCase(CharSequence camelCaseStr) {
        if (camelCaseStr == null) {
            return null;
        }

        final int length = camelCaseStr.length();
        StringBuilder sb = new StringBuilder();
        char c;
        boolean isPreUpperCase = false;
        for (int i = 0; i < length; i++) {
            c = camelCaseStr.charAt(i);
            boolean isNextUpperCase = true;
            if (i < (length - 1)) {
                isNextUpperCase = Character.isUpperCase(camelCaseStr.charAt(i + 1));
            }
            if (Character.isUpperCase(c)) {
                if (!isPreUpperCase || !isNextUpperCase) {
                    if (i > 0) {
                        sb.append("_");
                    }
                }
                isPreUpperCase = true;
            } else {
                isPreUpperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }
    /**
     * 切分字符串，如果分隔符不存在则返回原字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符
     * @return 字符串
     * @since 5.6.7
     */
    public static String[] splitToArray(CharSequence str, CharSequence separator) {
        if (str == null) {
            return new String[]{};
        }

        return StrSplitter.splitToArray(str.toString(), str(separator), 0, false, false);
    }


    /**
     * 切割指定位置之前部分的字符串
     *
     * @param string 字符串
     * @param toIndex 切割到的位置（不包括）
     * @return 切割后的剩余的前半部分字符串
     */
    public static String subPre(CharSequence string, int toIndex) {
        return sub(string, 0, toIndex);
    }
    /**
     * 改进JDK subString<br>
     * index从0开始计算，最后一个字符为-1<br>
     * 如果from和to位置一样，返回 "" <br>
     * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length<br>
     * 如果经过修正的index中from大于to，则互换from和to example: <br>
     * abcdefgh 2 3 =》 c <br>
     * abcdefgh 2 -3 =》 cde <br>
     *
     * @param str String
     * @param fromIndex 开始的index（包括）
     * @param toIndex 结束的index（不包括）
     * @return 字串
     */
    public static String sub(CharSequence str, int fromIndex, int toIndex) {
        if (isEmpty(str)) {
            return str(str);
        }
        int len = str.length();

        if (fromIndex < 0) {
            fromIndex = len + fromIndex;
            if (fromIndex < 0) {
                fromIndex = 0;
            }
        } else if (fromIndex > len) {
            fromIndex = len;
        }

        if (toIndex < 0) {
            toIndex = len + toIndex;
            if (toIndex < 0) {
                toIndex = len;
            }
        } else if (toIndex > len) {
            toIndex = len;
        }

        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        if (fromIndex == toIndex) {
            return EMPTY;
        }

        return str.toString().substring(fromIndex, toIndex);
    }
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
    //-----------------------------------------------------------------------------------------------------------------
    /**
     * 切分字符串，如果分隔符不存在则返回原字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符
     * @return 字符串
     * @since 5.7.1
     */
    public static List<String> split(CharSequence str, CharSequence separator) {
        return split(str, separator, false, false);
    }
    /**
     * 切分字符串
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     * @since 5.6.7
     */
    public static List<String> split(CharSequence str, CharSequence separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, 0, isTrim, ignoreEmpty);
    }
    /**
     * 切分字符串
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     * @since 3.2.0
     */
    public static List<String> split(CharSequence str, CharSequence separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        final String separatorStr = (null == separator) ? null : separator.toString();
        return StrSplitter.split(str(str), separatorStr, limit, isTrim, ignoreEmpty);
    }
    /**
     * 截取分隔字符串之前的字符串，不包括分隔字符串<br>
     * 如果给定的字符串为空串（null或""）或者分隔字符串为null，返回原字符串<br>
     * 如果分隔字符串为空串""，则返回空串，如果分隔字符串未找到，返回原字符串，举例如下：
     * <pre>
     * StrUtil.subBefore(null, *)      = null
     * StrUtil.subBefore("", *)        = ""
     * StrUtil.subBefore("abc", "a")   = ""
     * StrUtil.subBefore("abcba", "b") = "a"
     * StrUtil.subBefore("abc", "c")   = "ab"
     * StrUtil.subBefore("abc", "d")   = "abc"
     * StrUtil.subBefore("abc", "")    = ""
     * StrUtil.subBefore("abc", null)  = "abc"
     * </pre>
     * @param string 被查找的字符串
     * @param separator 分隔字符串（不包括）
     * @param isLastSeparator 是否查找最后一个分隔字符串（多次出现分隔字符串时选取最后一个），true为选取最后一个
     * @return 切割后的字符串
     */
    public static String subBefore(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (isEmpty(string) || separator == null) {
            return null == string ? null : string.toString();
        }

        final String str = string.toString();
        final String sep = separator.toString();
        if (sep.isEmpty()) {
            return EMPTY;
        }
        final int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (INDEX_NOT_FOUND == pos) {
            return str;
        }
        if (0 == pos) {
            return EMPTY;
        }
        return str.substring(0, pos);
    }
    /**
     * 截取分隔字符串之后的字符串，不包括分隔字符串<br>
     * 如果给定的字符串为空串（null或""），返回原字符串<br>
     * 如果分隔字符串为空串（null或""），则返回空串，如果分隔字符串未找到，返回空串
     * <pre>
     * StrUtil.subAfter(null, *)      = null
     * StrUtil.subAfter("", *)        = ""
     * StrUtil.subAfter(*, null)      = ""
     * StrUtil.subAfter("abc", "a")   = "bc"
     * StrUtil.subAfter("abcba", "b") = "cba"
     * StrUtil.subAfter("abc", "c")   = ""
     * StrUtil.subAfter("abc", "d")   = ""
     * StrUtil.subAfter("abc", "")    = "abc"
     * </pre>
     * @param string 被查找的字符串
     * @param separator 分隔字符串（不包括）
     * @param isLastSeparator 是否查找最后一个分隔字符串（多次出现分隔字符串时选取最后一个），true为选取最后一个
     * @return 切割后的字符串
     */
    public static String subAfter(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (isEmpty(string)) {
            return null == string ? null : string.toString();
        }
        if (separator == null) {
            return EMPTY;
        }
        final String str = string.toString();
        final String sep = separator.toString();
        final int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (pos == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }
}
