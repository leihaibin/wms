package com._520it.wms.util;

import com._520it.wms.domain.Employee;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

//存储当前登录用户的会话信息
public class UserContext {
    public static final String USER_IN_SESSION = "user_in_session";
    public static final String PERMISSION_IN_SESSION = "permission_in_session";

    private static HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession();
    }//cunchu sessiom中

    public static void setCurrentUser(Employee current) {
        if (current != null) {
            getSession().setAttribute(USER_IN_SESSION, current);
        } else {
            getSession().invalidate();
        }

    }

    public static Employee getCurrentUser() {

        return (Employee) getSession().getAttribute(USER_IN_SESSION);
    }
    //把当前用户的所有权限表达式存储到session中
    public static void setPermissionExpressions(List<String> exps) {
        getSession().setAttribute(PERMISSION_IN_SESSION, exps);
    }
    public static List<String> getPermissionExpressions() {
        return (List<String>) getSession().getAttribute(PERMISSION_IN_SESSION);
    }


}
