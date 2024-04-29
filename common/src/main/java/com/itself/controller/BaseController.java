package com.itself.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.itself.domain.EasyExcelSheet;
import com.itself.utils.FileUtil;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: duJi
 * @Date: 2024-04-15
 **/
@Slf4j
@Component
public class BaseController {

    /**
     * 同步导出excel文件
     * @param fileName 文件名称
     * @param excelType 导出excel文件类型
     * @param response 响应体
     * @param data  需要导出的数据结果集
     * @param head 对应的实体类
     */
    public void downloadExcel(String fileName,String excelType, HttpServletResponse response, Collection data, Class head){
        try {
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String encodeFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodeFileName+"."+excelType);

            ExcelTypeEnum excelTypeEnum = ExcelTypeEnum.XLSX;
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            if("xls".equals(excelType)){
                excelTypeEnum = ExcelTypeEnum.XLS;
                response.setContentType("application/vnd.ms-excel");
            }else if("csv".equals(excelType)){
                excelTypeEnum = ExcelTypeEnum.CSV;
                response.setContentType("text/csv");
            }
            EasyExcel.write(response.getOutputStream(), head).excelType(excelTypeEnum).sheet(fileName).doWrite(data);
        } catch (Exception e) {
            log.error("文件【{}】下载失败", fileName);
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 导出为多sheet页excel数据，数据量不大，可为多数据表结果集
     * @param fileName 文件名称 如：personal用户管理xlsx
     * @param response  响应体
     * @param sheets 封装后的多sheet页model
     */
    public void downloadMultipleSheetExcel(String fileName, HttpServletResponse response, List<EasyExcelSheet> sheets){
        try {
            response.setCharacterEncoding("utf-8");
            String excelType = FileUtil.extName(fileName);
            String sheetName = FileUtil.mainName(fileName);

            // 这里URLEncoder.encode可以防止中文乱码
            String encodeFileName = URLEncoder.encode(sheetName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodeFileName + "." + excelType);

            ExcelTypeEnum excelTypeEnum = ExcelTypeEnum.XLSX;
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            if ("xls".equals(excelType)) {
                excelTypeEnum = ExcelTypeEnum.XLS;
                response.setContentType("application/vnd.ms-excel");
            } else if ("csv".equals(excelType)) {
                excelTypeEnum = ExcelTypeEnum.CSV;
                response.setContentType("text/csv");
            }
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).excelType(excelTypeEnum).build();
            sheets.forEach(sheet -> {
                // sheet页位置，sheet页名称
                WriteSheet writeSheet = EasyExcel.writerSheet(sheet.getSheetIndex(), sheet.getSheetName())
                        // 表头实体
                        .head(sheet.getHeadClass())
                        // 导出内容格式
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .build();
                excelWriter.write(sheet.getDataset(), writeSheet);
            });
            excelWriter.finish();
            response.flushBuffer();
        } catch (Exception e) {
            log.error("文件【{}】下载失败", fileName);
            log.error(e.getMessage(), e);
        }
    }

}
