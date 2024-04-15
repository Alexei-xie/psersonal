package com.itself.annotation.validator;

import com.itself.result.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**使用ApiFox工具进行请求
 * @Author xxw
 * @Date 2022/12/09
 */
@Api(tags = "自定义注解")
@RestController
@RequestMapping("/validator")
public class ValidatorController {

    @PostMapping("/test")
    @ApiOperation("测试校验接口")
    public Response<Boolean> test(@RequestBody @Valid Company company){
        return Response.ok("validator");
    }
}
