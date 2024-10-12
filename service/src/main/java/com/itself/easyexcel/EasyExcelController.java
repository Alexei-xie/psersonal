package com.itself.easyexcel;

import com.alibaba.fastjson.JSONObject;
import com.itself.controller.BaseController;
import com.itself.domain.EasyExcelSheet;
import com.itself.enums.ApiCode;
import com.itself.result.Response;
import com.itself.user.entity.UserPO;
import com.itself.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Apifox请求示例
 * @Author: duJi
 * @Date: 2024-04-15
 **/
@Api(tags = "easyExcel测试接口")
@RestController
@RequestMapping("/easyexcel/test")
public class EasyExcelController extends BaseController {

    @Resource
    private UserService userService;;

    @ApiOperation("单sheet页导入Excel")
    @PostMapping("/import/simple")
    public Response<Object> importExcelSimple(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("files"); // 获取上传文件对象
            List<String> errorMsg = userService.importData(file.getInputStream());
            if (CollectionUtils.isEmpty(errorMsg)){
                return Response.ok(List.of(),"导入成功");
            }
            return Response.error(ApiCode.FAIL,errorMsg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @ApiOperation("多sheet页导入Excel")
    @PostMapping("/import/sheets")
    public Response<Object> importExcelSheets(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("files"); // 获取上传文件对象
            List<String> errorMsg = userService.importDataSheets(file.getInputStream());
            if (CollectionUtils.isEmpty(errorMsg)){
                return Response.ok(List.of(),"导入成功");
            }
            return Response.error(ApiCode.FAIL,errorMsg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     *
     * @param jsonObject    String excelType = jsonObject.getStr("excelType");
     *                      String ids = jsonObject.getStr("ids");
     * @param response
     */
    @ApiOperation("选择导出Excel单sheet页")
    @PostMapping("/exportSync")
    public void exportExcelSync(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        String excelType = "xlsx";
        List<UserPO> data = userService.listAll();
        downloadExcel("personal用户管理" , excelType, response, data, UserPO.class);
    }
    @ApiOperation("选择导出Excel多sheet页")
    @PostMapping("/exportSheets")
    public void exportExcelSheets(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        String excelType = ".xlsx";
        List<EasyExcelSheet> excelSheets = initData();
        downloadMultipleSheetExcel("personal用户管理" + excelType, response, excelSheets);
    }

    /**
     * 构建多sheet页model
     */
    private List<EasyExcelSheet> initData(){
        List<UserPO> data = userService.listAll();
        List<UserPO> data1 = userService.listAll();
        EasyExcelSheet dictVoSheet = EasyExcelSheet.builder()
                .sheetIndex(0)
                .sheetName("用户管理1")
                .headClass(UserPO.class)
                .dataset(data)
                .build();
        EasyExcelSheet itemDataSheet = EasyExcelSheet.builder()
                .sheetIndex(1)
                .sheetName("用户管理2")
                .headClass(UserPO.class)
                .dataset(data1)
                .build();
        return List.of(dictVoSheet,itemDataSheet);
    }

    @ApiOperation("异步导出Excel")
    @PostMapping("/exportAsync")
    public void exportExcelAsync() {

    }

    @ApiOperation("下载模板")
    @PostMapping("/downloadTemplate")
    public void downloadTemplate(  ) {
    }


}
