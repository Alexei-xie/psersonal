package com.itself.domain;

import java.util.Collection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 多sheet页导出model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EasyExcelSheet {
    /**
     * sheet页位置
     */
    private Integer sheetIndex;
    /**
     * sheet页名称
     */
    private String sheetName;
    /**
     * 表头实体
     */
    private Class headClass;

    /**
     * 导出的数据集
     */
    private Collection dataset;
}