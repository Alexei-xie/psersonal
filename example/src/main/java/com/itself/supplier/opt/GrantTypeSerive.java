package com.itself.supplier.opt;

import org.springframework.stereotype.Component;

@Component
public class GrantTypeSerive {

    public String redPaper(String resourceId){
        // 红包的发放方式
        return "每周末9点发放"+resourceId;
    }
    public String shopping(String resourceId){
        // 购物券的发放方式
        return "每周三9点发放"+resourceId;
    }
    public String QQVip(String resourceId){
        // qq会员的发放方式
        return "每周一0点开始秒杀"+resourceId;
    }
}