package com._520it.wms.web.controller;

import com._520it.wms.query.QueryObject;
import com._520it.wms.service.PermissionService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @RequestMapping("/list")
    @RequiredPermission("权限列表")
    public String list(QueryObject qo,Model model){
        model.addAttribute("pageResult",permissionService.query(qo));
        return "/permission/list";
    }
   
    @RequestMapping("/delete")
    @RequiredPermission("删除权限")
    @ResponseBody
    public JSONResult delete(Long id){
        if(id!=null) {
            permissionService.delete(id);
        }
        return new JSONResult();
    }

    @RequestMapping("/reload")
    @RequiredPermission("权限加载")
    @ResponseBody
    public JSONResult reload(){
        permissionService.reload();
        return new JSONResult();
    }

}
