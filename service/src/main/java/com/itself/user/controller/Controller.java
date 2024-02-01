package com.itself.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itself.user.entity.UserPO;
import com.itself.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xxw
 * @Date 2023/07/19
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "接口测试类")
public class Controller {
    @Resource
    private UserService userService;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @GetMapping("/test")
    @ApiOperation("test方法")
    public void printResult() {
        log.info("start");
        System.out.println("result=" + userService.listAll());
        taskExecutor.execute(() -> {
            for (int i = 0; i < 10; i++) {
                log.info(String.valueOf(i));
                if (i == 5) {
                    System.out.println(i / 0);
                }
            }
        });
        log.info("=====");
    }

    @ApiOperation("updateAndInsert")
    @GetMapping("/update-insert")
    public void updateAndInsert(){
        Page<UserPO> page = userService.listPage(1, 5);
        List<UserPO> data = page.getRecords();
        data.get(0).setAge(18).setName("duJi");
        data.add(new UserPO().setName("xxw").setAge(25).setSex("M").setPrice(new BigDecimal("100000000")));
        userService.saveOrUpdateBatch(page.getRecords());
    }
}
