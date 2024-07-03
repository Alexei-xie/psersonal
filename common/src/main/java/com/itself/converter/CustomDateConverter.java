package com.itself.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Excel日期转换器
 * ，使用： ExcelReaderBuilder.registerConverter(new CustomDateConverter())
 */
public class CustomDateConverter implements Converter<Date> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public Class<?> supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return DateUtils.parseDate(cellData.getStringValue(), DATE_FORMAT);
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Date> context) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return new WriteCellData<>(formatter.format(context.getValue()));
    }


}