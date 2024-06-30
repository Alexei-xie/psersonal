package com.itself.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.itself.annotation.Dict;
import static com.itself.annotation.aspect.DictAspect.DICT_MAP;
import static com.itself.annotation.aspect.DictAspect.ITEM_MAP;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * EasyExcel数据字典转换器
 * @Author duJi
 * @Date 2024-06-29
 */
@Slf4j
public class DictConverter implements Converter<Object> {

    /**
     * 将Java对象转为Excel对象：Excel导出
     * @param value               Java Data.NotNull.
     * @param contentProperty     Content property.Nullable.
     * @param globalConfiguration Global configuration.NotNull.
     * @return
     * @throws Exception
     */
    @Override
    public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        // 获取当前字段
        Field field = contentProperty.getField();
        // 获取当前字段的数据字典注解
        Dict annotation = field.getAnnotation(Dict.class);
        if (annotation == null){
            log.error("当前字段没有数据字典注解，不支持转化，返回原值");
            return new WriteCellData<>(String.valueOf(value));
        }
        log.info("数据字典Code是:{}",annotation.code());
        //根据数据字典Code获取数据字典项明细
        Map<String, String> dictItem = DICT_MAP.get(annotation.code());
        //根据字典项Code获取字典项名称值
        String itemValue = dictItem.getOrDefault(String.valueOf(value), "");
        log.info("数据字典项名称是:{}",itemValue);
        return new WriteCellData<>(itemValue);
    }

    /**
     * 将Excel对象转为Java对象：Excel导入
     * @param cellData            Excel cell data.NotNull.
     * @param contentProperty     Content property.Nullable.
     * @param globalConfiguration Global configuration.NotNull.
     * @return
     */
    @Override
    public Object convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        // 获取当前字段
        Field field = contentProperty.getField();
        // 获取当前字段的数据字典注解
        Dict annotation = field.getAnnotation(Dict.class);
        if (annotation == null){
            log.error("当前字段没有数据字典注解，不支持转化，返回原值");
            return cellData.getStringValue();
        }
        log.info("数据字典Code是:{}",annotation.code());
        //根据数据字典Code获取数据字典项明细
        Map<String, String> dictItem = ITEM_MAP.get(annotation.code());
        //根据字典项值获取字典项Code
        String itemCode = dictItem.getOrDefault(cellData.getStringValue(), "");
        log.info("数据字典项Code是:{}",itemCode);
        return itemCode;
    }
}
