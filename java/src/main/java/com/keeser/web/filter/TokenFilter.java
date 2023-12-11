package com.keeser.web.filter;

import com.keeser.web.utils.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * 自定义jwt全局过滤器
 * 1.没有携带token放行
 * 2.携带token,将用户信息添加至security上下文中
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtTokenUtil jwtUtil = new JwtTokenUtil();

        //获取当前请求的uri
        String uri = request.getRequestURI();
        //判断是否是认证请求路径
        //是：直接放行
        if (uri.endsWith("api/login") || uri.endsWith("api/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        //否：获取请求头中携带的token
        String authorization = request.getHeader("Authorization");

        //判断是否携带token
        //否：抛出异常
        if (StringUtils.isBlank(authorization)) {
            return;
        }

        String realToken = authorization.replace("Bearer ", "");

        //是：校验jwt有效性
        if (!jwtUtil.checkToken(realToken)) {
            return;
        }

        filterChain.doFilter(request, response);
    }
}