package com.itself.interceptor;

import com.itself.domain.User;
import com.itself.utils.ThreadLocalUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 自定义拦截器
 * @Author xxw
 * @Date 2023/08/05
 */
@Slf4j
@Configuration
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().contains("/swagger-") || request.getRequestURI().contains("/v2")){
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        log.info("preHandle: {}" , request.getRequestURI());
        //使用demo  一次请求从开始到结束，会从上之下依次执行 preHandle postHandle afterCompletion
        String userId = request.getHeader("userId");
        if (StringUtils.isNotBlank(userId)){
            ThreadLocalUtil.setUser(new User().setUserId(userId));
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    // @Override
    // public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    //     ThreadLocalUtil.clear();
    //     System.out.println("postHandle:" +request.getRequestURI());
    //     HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    // }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (!request.getRequestURI().contains("/swagger-") && !request.getRequestURI().contains("/v2")){
            log.info("afterCompletion: {}", request.getRequestURI());
            ThreadLocalUtil.clear();
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
