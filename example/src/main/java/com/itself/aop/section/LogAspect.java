package com.itself.aop.section;

import com.alibaba.fastjson.JSON;
import com.itself.aop.annotation.OperationLogDetail;
import com.itself.aop.model.OperationLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author xxw
 * @Date 2021/08/17
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     */
    @Pointcut("execution(* com.itself.aop.service.impl.*.*(..))")
    //    @Pointcut("@annotation(com.itself.annotation.OperationLogDetail)")
    public void operationLog() {
    }
    @Before("operationLog()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("1.进入方法前执行.....");

    }
    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res = null;
        long time = System.currentTimeMillis();
        try {
            //获取执行后的返回值
            res = joinPoint.proceed();
            time = System.currentTimeMillis() - time;
            return res;
        } finally {
            // 封装日志信息
            addOperationLog(joinPoint, res, time);
        }
    }

    private void addOperationLog(ProceedingJoinPoint joinPoint, Object res, long time) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        OperationLog operationLog = new OperationLog();//此处将一些数据保存到操作日志中
        operationLog.setRunTime(time);
        operationLog.setReturnValue(JSON.toJSONString(res));
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setArgs(JSON.toJSONString(joinPoint.getArgs()));
        operationLog.setCreateTime(new Date());
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        operationLog.setUserId("#{currentUserId}");//此处占位符好像没有用
        operationLog.setUserName("#{user}");//此处占位符好像没有用
        OperationLogDetail annotation = signature.getMethod().getAnnotation(OperationLogDetail.class);
        if (annotation != null) {
            operationLog.setLevel(annotation.level());
            operationLog.setDescribe(
                getDetail(((MethodSignature)joinPoint.getSignature()).getParameterNames(), joinPoint.getArgs(),
                    annotation));
            operationLog.setOperationType(annotation.operationType().getValue());
            operationLog.setOperationUnit(annotation.operationUnit().getValue());
        }
        // TODO 这里保存日志
        System.out.println("5.记录日志：" + operationLog);
    }

    /**
     * 对当前登录用户和占位符处理
     *
     * @param argNames   方法参数名称数组
     * @param args       方法参数数组
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    private String getDetail(String[] argNames, Object[] args, OperationLogDetail annotation) {

        //将参数转为map存储：argNames为key 即参数名 args为value 即传参值
        Map<Object, Object> map = new HashMap<>(4);
        for (int i = 0; i < argNames.length; i++) {
            map.put(argNames[i], args[i]);
        }
        String detail = annotation.detail();
        try {
            //利用字符串替换来获取注解详细信息
            detail = "'" + "#{user}" + "'=>" + annotation.detail();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object k = entry.getKey();
                Object v = entry.getValue();
                detail = detail.replace("{" + k + "}", JSON.toJSONString(v));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 处理完请求，返回内容
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "operationLog()")
    public void doAfterReturning(Object ret) {
        System.out.println("3.方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("operationLog()")
    public void throwss(JoinPoint jp) {
        System.out.println("方法异常时执行.....");
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("operationLog()")
    public void after(JoinPoint jp) {
        System.out.println("4.方法最后执行.....");
    }
}
