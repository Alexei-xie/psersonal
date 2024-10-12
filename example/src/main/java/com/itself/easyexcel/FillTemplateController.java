package com.itself.easyexcel;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.itself.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ApiFox请求
 * 数据模板填充--模板数据填充示例.xlsx
 * https://blog.csdn.net/btt2013/article/details/121523856
 * @Author duJi
 * @Date 2024-10-12
 */
@Api(tags = "excel模板数据填充")
@RestController
@RequestMapping("/excelFillData")
public class FillTemplateController {

    @ApiOperation("fillTemplate")
    @PostMapping(value = "/fillTemplate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void fillTemplate(@RequestParam("file") MultipartFile file, HttpServletResponse response)  {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
        inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();// 模板数据填充示例.xlsx
        String prefixName = FileUtil.mainName(fileName);// 模板数据填充示例
        String fileType = FileUtil.extName(fileName);
        // 目标文件名称
        String targetFileName = prefixName + "-" + DateUtil.format(new Date(), "yyyyMMdd") + "." + fileType;// 模板数据填充示例-2024-10-12-12:12:12.xlsx
        // Map<String, String> map = new HashMap<>();
        // map.put("name", "李四" );
        // map.put("age", "11" );
        // map.put("remark", "test" );
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String encodedFileName = URLEncoder.encode(targetFileName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName);

        // 获取输出流
        outputStream = response.getOutputStream();
        // 准备Map数据填充
        List<Map<String, String>> data = this.initData();
        // 生成工作簿对象
        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
        excelWriter.fill(new FillWrapper("stu", data), writeSheet);
        excelWriter.finish();
        outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Map<String, String>> initData() {
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", "李四" + i);
            map.put("age", "11" + i);
            map.put("remark", "test" + i);
            data.add(map);
        }
        return data;
    }
}
