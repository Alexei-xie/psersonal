package com.itself.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * Excel工具类
 * @Author: duJi
 * @Date: 2024-05-06
 **/
@Slf4j
public class ExcelUtils {
    /**
     * 判断整行单元格数据是否均为空
     */
    public static <T> boolean isLineNullValue(T data) {
        try {
            List<Field> fields = Arrays.stream(data.getClass().getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(ExcelProperty.class))
                    .collect(Collectors.toList());
            List<Boolean> lineNullList = new ArrayList<>(fields.size());
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(data);
                if (Objects.isNull(value)) {
                    lineNullList.add(Boolean.TRUE);
                } else {
                    lineNullList.add(Boolean.FALSE);
                }
            }
            return lineNullList.stream().allMatch(Boolean.TRUE::equals);
        } catch (Exception e) {
            log.error("读取数据行[{}]解析失败: {}", data, e.getMessage());
        }
        return true;
    }
}
