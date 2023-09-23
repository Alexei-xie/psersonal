package com.itself.supplier.opt;

import com.itself.supplier.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueryGrantTypeService {
 
    @Autowired
    private GrantTypeSerive grantTypeSerive;
    private final Map<String, Function<String,String>> grantTypeMap=new HashMap<>();

    /**
     *  初始化业务分派逻辑,代替了if-else部分
     *  key: 优惠券类型
     *  value: lambda表达式,最终会获得该优惠券的发放方式
     *  @PostConstruct 注解只能用于非静态方法，且只能用于一次，即同一个类中不能有多个方法使用
     *  @PostConstruct 注解。同时，这个方法不能有任何参数，且返回值类型必须是 void
     */
    @PostConstruct
    public void dispatcherInit(){
        grantTypeMap.put("redEnvelope",resourceId->grantTypeSerive.redPaper(resourceId));
        grantTypeMap.put("memberCoupon",resourceId->grantTypeSerive.shopping(resourceId));
        grantTypeMap.put("QQMember",resourceId->grantTypeSerive.QQVip(resourceId));
    }
 
    public String getResult(String resourceType,String resourceId){
        // Controller根据 优惠券类型resourceType、编码resourceId 去查询 发放方式grantType
        Function<String,String> result=grantTypeMap.get(resourceType);
        if(result!=null){
         // 传入resourceId 执行这段表达式获得String型的grantType
            return result.apply(resourceId);
        }
        return "查询不到该优惠券的发放方式";
    }
}