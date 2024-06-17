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
     * 纳税人识别号Id COLUMN:TAXPAYER_ID
     */
    private String taxpayerId;
    /**
     * 纳税人识别号 COLUMN:TAXPAYER_NO
     */
    private String taxpayerNo;
    /**
     * 纳税人名称 COLUMN:TAXPAYER_NAME
     */
    private String taxpayerName;

    /**
     * 申报年份 COLUMN:DECLARE_YEAR
     */
    private Integer declareYear;

    /**
     * 申报期间 COLUMN:DECLARE_PERIOD
     */
    private Integer declarePeriod;
    /**
     * 公司代码 COLUMN:COMPANY_CODE
     */
    private String companyCode;

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
