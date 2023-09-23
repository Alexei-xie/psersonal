package com.itself.aop.demo.aspect;

import com.itself.aop.demo.annotation.MyAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class MyAspect {
    /**
     * 方法将会在方法执行前被调用
     */
    @Before("@annotation(myAnnotation)")
    public void logMethodArguments(JoinPoint joinPoint , MyAnnotation myAnnotation) {
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法参数的名称
        String[] parameterNames = methodSignature.getParameterNames();
        // 获取方法参数的值
        Object[] parameterValues = joinPoint.getArgs();
        // 处理参数
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            Object parameterValue = parameterValues[i];
            System.out.println("Parameter: " + parameterName + " = " + parameterValue);
        }
        //获取注解属性
        String name = myAnnotation.name();
        int times = myAnnotation.times();
        String urlPath = myAnnotation.urlPath();
    }

    /**
     * Around通知方法logMethodArguments的返回值是Object类型，通过调用proceed()方法继续执行目标方法，并获取返回结果。根据需要对返回结果进行处理，然后将其返回
     */
    @Around("@annotation(myAnnotation)")
    public Object logMethodArguments(ProceedingJoinPoint joinPoint , MyAnnotation myAnnotation) throws Throwable {

        //获取注解属性
        String name = myAnnotation.name();
        int times = myAnnotation.times();
        String urlPath = myAnnotation.urlPath();

        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法参数的名称
        String[] parameterNames = methodSignature.getParameterNames();
        // 获取方法参数的值
        Object[] parameterValues = joinPoint.getArgs();
        // 处理参数
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            Object parameterValue = parameterValues[i];
            System.out.println("Parameter: " + parameterName + " = " + parameterValue);
        }
        // 继续执行目标方法
        Object result = joinPoint.proceed();
        // 可以对目标方法的返回值进行处理
        System.out.println("Result: " + result);
        // 返回目标方法的执行结果
        return result;
    }

    /**
     * AfterReturning通知在目标方法成功执行并返回结果后执行。你可以使用它来处理目标方法的返回值，进行一些日志记录、资源清理等操作
     * 该方法无法修改目标方法的返回值，只能对返回值进行处理或记录
     */
    @AfterReturning(pointcut = "@annotation(myAnnotation)", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result, MyAnnotation myAnnotation) {
        // 处理目标方法的返回值
        System.out.println("Result: " + result);
    }

    /**
     * AfterThrowing通知在目标方法抛出异常后执行。你可以使用它来处理异常、记录错误信息、发送通知
     * 该方法无法处理或捕获异常，它只能在目标方法抛出异常时进行一些后置操作
     */
    @AfterThrowing(pointcut = "@annotation(myAnnotation)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex, MyAnnotation myAnnotation) {
        // 处理目标方法抛出的异常
        System.out.println("Exception: " + ex.getMessage());
    }
}