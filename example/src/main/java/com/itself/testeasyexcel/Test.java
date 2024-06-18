package com.itself.testeasyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.itself.testeasyexcel.bean.BalanceSheetVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @Author: duJi
 * @Date: 2024-06-17
 **/
@RestController
@Api(tags = "test11111")
@RequestMapping("/excel")
public class Test {



    @GetMapping("/test")
    @ApiOperation("132131")
    public String testMock(){
        return "122;";
    }


    @PostMapping("/import")
    public String test(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("files"); // 获取上传文件对象
        // .headRowNumber(0)表示从第1行开始读
        // LinkedHashMap<Integer,String>表示一行里面有多少列
        List<LinkedHashMap<Integer,String>> data = EasyExcel.read(file.getInputStream()).sheet().headRowNumber(0).doReadSync();
        LinkedHashMap<Integer, String> dateMap = data.get(1);//读取excel里的第二行数据
        String date = dateMap.get(0);//读取第一列数据
        LinkedHashMap<Integer, String> companyMap = data.get(2);//读取excel里的第三行数据
        String company = companyMap.get(0);

        List<BalanceSheetVO> datas = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), BalanceSheetVO.class, new AnalysisEventListener<BalanceSheetVO>() {
            @Override
            public void invoke(BalanceSheetVO vo, AnalysisContext context) {
                datas.add(vo);
            }
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 最后会走这里，在这里做新增
                System.out.println(datas);
            }
        }).sheet().headRowNumber(4).doRead();//索引4，实际是第5行，详情可见 资产负债表excel

        // 第三行第二列（B3）
        return "success";
    }
}
