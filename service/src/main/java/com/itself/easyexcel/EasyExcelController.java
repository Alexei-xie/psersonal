package com.itself.easyexcel;

import com.itself.enums.ApiCode;
import com.itself.result.Response;
import com.itself.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequiredArgsConstructor
public class EasyExcelController {

    private final UserService userService;;

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
    @ApiOperation("导出Excel")
    @PostMapping("/export")
    public void exportExcel(@RequestParam MultipartFile multipartFile) {
    }

    @ApiOperation("下载模板")
    @PostMapping("/downloadTemplate")
    public void downloadTemplate(@RequestParam MultipartFile multipartFile) {
    }


}
