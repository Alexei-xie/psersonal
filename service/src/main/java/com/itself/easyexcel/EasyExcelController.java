package com.itself.easyexcel;

import com.alibaba.fastjson.JSONObject;
import com.itself.controller.BaseController;
import com.itself.enums.ApiCode;
import com.itself.user.entity.UserPO;
import com.itself.user.service.UserService;
import com.itself.result.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @Author: duJi
 * @Date: 2024-04-15
 **/
@Api(tags = "easyExcel测试接口")
@RestController
@RequestMapping("/easyexcel/test")
public class EasyExcelController extends BaseController {

    @Resource
    private UserService userService;;

    @ApiOperation("导入Excel")
    @PostMapping("/import")
    public Response<Object> importExcel(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("files"); // 获取上传文件对象
            List<String> errorMsg = userService.importData(file.getInputStream());
            if (CollectionUtils.isEmpty(errorMsg)){
                return Response.ok(Lists.newArrayList(),"导入成功");
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
    @ApiOperation("同步导出Excel")
    @PostMapping("/exportSync")
    public void exportExcelSync(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        String excelType = "xlsx";
        List<UserPO> data = userService.listAll();
        downloadExcel("personal用户管理" , excelType, response, data, UserPO.class);
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
