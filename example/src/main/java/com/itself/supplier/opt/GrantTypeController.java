package com.itself.supplier.opt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *  <p>https://mp.weixin.qq.com/s/uxl14x3EY_fUiHSlp-lCGA</p>
 */
@RestController
public class GrantTypeController {

    @Autowired
    private QueryGrantTypeService queryGrantTypeService;

    /**
     * 访问路径：http://localhost:1212/grantType/redEnvelope/2
     */
    @GetMapping("/grantType/{key}/{value}")
    public String test(@PathVariable String key,@PathVariable String value){
        return queryGrantTypeService.getResult(key,value);
    }
}