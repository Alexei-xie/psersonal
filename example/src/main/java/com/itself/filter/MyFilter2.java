package com.itself.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author xxw
 * @Date 2023/01/11
 */
@Order(2)
// @WebFilter(filterName = "MyFilter2",urlPatterns = {"/*"})
public class MyFilter2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter.super.init(filterConfig);
        System.out.println("other filter  initialization");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("other filter in target ");
        chain.doFilter(request,response);
        System.out.println("other filter handle response");
    }

    @Override
    public void destroy() {
        // Filter.super.destroy();
        System.out.println("other filter destroyed");
    }
}
