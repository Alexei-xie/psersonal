package com.itself.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itself.domain.User;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xxw
 * @Date 2023/07/21
 */
public class ConvertUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String TYPE = "\"";

    /**
     * Object 转 map
     */
    public static <K, V> Map<K, V> convertToMap(Object obj) {
        Map<K, V> map;
        if (obj instanceof String) {
            map = parseJsonToMap(obj.toString());
        } else {
            map = convertObject(obj, new TypeReference<Map<K, V>>() {
            });
        }
        return map != null ? map : new HashMap<>();
    }

    public static <T> T convertObject(Object obj, TypeReference<T> type) {
        return mapper.convertValue(obj, type);
    }

    /**
     * json 转 map
     */
    public static <K, V> Map<K, V> parseJsonToMap(String jsonStr) {
        Map<K, V> map = parseJson(jsonStr, new TypeReference<Map<K, V>>() {
        });
        if (map != null) {
            return map;
        }
        return Collections.emptyMap();
    }


    /**
     * 格式化json数据
     */
    public static <T> T parseJson(String jsonStr, TypeReference<T> type) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        String str = getJsonValue(jsonStr);
        try {
            return mapper.readValue(str, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getJsonValue(String str) {
        String s = str;
        if (s.startsWith(TYPE)) {
            s = s.substring(1);
        }
        if (s.endsWith(TYPE)) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public static void main(String[] args) throws JsonProcessingException {
        User user = new User().setName("xw").setAge("18");
        System.out.println(user.toString());
        System.out.println(ConvertUtil.convertToMap(user));
    }

}
