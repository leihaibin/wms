package com._520it.wms.web.controller;

import com._520it.wms.service.EmployeeService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/login")
    @ResponseBody
    public JSONResult login(String username, String password) {
        JSONResult jsonResult = new JSONResult();
        try {
            employeeService.checkLogin(username, password);
        } catch (Exception e) {
            jsonResult.mark(e.getMessage());
            return jsonResult;
        }
        return jsonResult;
    }
    //登入成功跳转到主界面
    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    //注销
    @RequestMapping("/logout")
    public String login(){
        UserContext.setCurrentUser(null);
        return "redirect:/login.jsp";
    }

}
