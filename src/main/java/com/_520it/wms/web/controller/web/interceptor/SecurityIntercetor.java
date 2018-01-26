package com._520it.wms.web.controller.web.interceptor;

import com._520it.wms.domain.Employee;
import com._520it.wms.util.RequiredPermission;
import com._520it.wms.util.SystemException;
import com._520it.wms.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//登入检查拦截器
public class SecurityIntercetor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Employee current = UserContext.getCurrentUser();
        //判断当前用户手否是超级管理员,是则放行
        if (current.getAdmin()) {
            return true;
        }
        //2.当前请求的方法上是否有RequiredPermission注解
        HandlerMethod hm = (HandlerMethod) handler;
        if (!hm.getMethod().isAnnotationPresent(RequiredPermission.class)) {
            return true;
        }
        //3.获取Controller方法的权限表达式
        String exp = hm.getMethod().getDeclaringClass().getName() + ":" + hm.getMethod().getName();
        //4.判断exp是否在session中
        List<String> exps = UserContext.getPermissionExpressions();
        if (exps.contains(exp)) {
            return true;
        }
        throw new SystemException("亲,你没有访问的权限!请联系管理员!");//跳转到权限不足页面
    }

}
