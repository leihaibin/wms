package com._520it.wms.web.controller.web.interceptor;

import com._520it.wms.domain.Employee;
import com._520it.wms.util.UserContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登入检查拦截器
public class CheckLoginIntercetor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断session中的user_in_session是否为null,是就是没有登录,跳转到login.jsp,否则放行
        Employee current = UserContext.getCurrentUser();
        if (current == null) {
            response.sendRedirect("/login.jsp");
            return false;
        }

        return true;
    }

}
