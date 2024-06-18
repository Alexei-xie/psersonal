package com.itself.testeasyexcel.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: duJi
 * @Date: 2024-06-17
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceSheetVO {
    /**
     * 报表名称 COLUMN:REPORT_NAME
     */
    private String reportName;

    @ExcelProperty(value = "资产",index = 0)
    private String asset;

    @ExcelProperty(value = "期初数",index = 1)
    private String atBeginning;

    @ExcelProperty(value = "期末数",index = 2)
    private String atEndOfPeriod;

    @ExcelProperty(value = "负债及所有者权益",index = 4)
    private String liability;

    @ExcelProperty(value = "期初数",index = 5)
    private String liabilityAtBeginning;

    @ExcelProperty(value = "期末数",index = 6)
    private String liabilityAtEndOfPeriod;
}
