package com.itself.annotation.redis;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 *  重复提交AOP
 * @Author xxw
 * @Date 2022/10/17
 */
@Aspect
@Component
@Slf4j
public class RepeatSubmitAspect {
    @Resource
    private RedisLock redisLock;

    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
    }

    @Around("pointCut(noRepeatSubmit)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        int lockSeconds = noRepeatSubmit.lockTime();

        // HttpServletRequest request = RequestUtils.getRequest();
        HttpServletRequest request = null;
        Assert.notNull(request, "request can not null");

        String bearerToken = request.getHeader("Authorization");
        String[] tokens = bearerToken.split(" ");
        String token = tokens[1];
        String path = request.getServletPath();
        String key = getKey(token, path);
        String clientId = getClientId();

        boolean isSuccess = redisLock.tryLock(key, clientId, lockSeconds);
        log.info("tryLock key = [{}], clientId = [{}]", key, clientId);
        if (isSuccess) {
            log.info("tryLock success, key = [{}], clientId = [{}]", key, clientId);
            // 获取锁成功
            Object result;
            try {
                // 执行进程
                result = pjp.proceed();
            } finally {
                // 解锁
                redisLock.releaseLock(key, clientId);
                log.info("releaseLock success, key = [{}], clientId = [{}]", key, clientId);
            }
            return result;
        } else {
            // 获取锁失败，认为是重复提交的请求
            log.info("tryLock fail, key = [{}]", key);
            // return  ApiResult.fail("重复请求，请稍后再试");
            return  null;
        }
    }
    private String getKey(String token, String path) {
        return token + path;
    }
    private String getClientId() {
        return UUID.randomUUID().toString();
    }
}
