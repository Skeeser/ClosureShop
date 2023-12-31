package com.keeser.web.filter;

import com.keeser.web.entity.User;
import com.keeser.web.service.UserService;
import com.keeser.web.utils.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtTokenUtil jwtUtil = new JwtTokenUtil();

        //获取当前请求的uri
        String uri = request.getRequestURI();

        // 判断当前是什么请求, 如果是OPTIONS放行
        if(request.getMethod().equals("OPTIONS")){
            filterChain.doFilter(request, response);
            return;
        }

        //判断是否是认证请求路径
        // 是：直接放行
        if (uri.endsWith("api/login") || uri.endsWith("api/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 否：获取请求头中携带的token
        String authorization = request.getHeader("Authorization");

        // 判断是否携带token
        // 否：抛出异常
        if (StringUtils.isBlank(authorization)) {
            return;
        }
        // String realToken = authorization.replace("Bearer ", "");
        // 是:校验jwt有效性
        if (!jwtUtil.checkToken(authorization)) {
            return;
        }
        // 根据token获取用户信息
        String userName = jwtUtil.getUserName(authorization);
        User user = userService.getByName(userName);
        // 将用户信息存储在全局的安全上下文中
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user, null, null
                ));

        filterChain.doFilter(request, response);
    }
}