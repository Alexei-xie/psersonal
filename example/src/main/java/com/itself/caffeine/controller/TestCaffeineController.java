package com.itself.caffeine.controller;

import com.github.benmanes.caffeine.cache.Cache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: duJi
 * @Date: 2024-01-02
 **/
@RequestMapping("/caffeine")
@RestController
@Api(tags = "caffeine接口测试")
public class TestCaffeineController {
    @Resource
    private Cache<String, Object> caffeineCache;

    @ApiOperation("put缓存")
    @GetMapping("/put")
    public void putCache(){
        for (int i = 0; i < 3; i++) {
            caffeineCache.put(String.valueOf(i),"result"+i);
        }

    }
    @ApiOperation("get缓存")
    @GetMapping("/get/{param}")
    public String getCache(@PathVariable String param){
        // caffeineCache.getIfPresent(param); 如果有key则返回，如果没有key则返回null
       return (String) caffeineCache.get(param, e-> param);//这个方法是如果缓存中没有该key，则重新放入该key并返回后续执行的result
    }
}
