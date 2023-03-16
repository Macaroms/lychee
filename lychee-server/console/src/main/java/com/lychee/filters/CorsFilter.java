package com.lychee.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/4/10 12:32
 */
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse rep = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String origin = req.getHeader("Origin");
        rep.setHeader("Access-Control-Allow-Origin", origin);
        rep.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
        rep.setHeader("Access-Control-Allow-Credentials", "true");
        rep.setHeader(
                "Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization")
        ;
        rep.setHeader("Access-Control-Max-Age", "3600");
        rep.setHeader("XDomainRequestAllowed", "1");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
