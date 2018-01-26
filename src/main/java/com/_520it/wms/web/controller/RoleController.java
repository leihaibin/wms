package com._520it.wms.web.controller;

import com._520it.wms.domain.Role;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.PermissionService;
import com._520it.wms.service.RoleService;
import com._520it.wms.service.SystemMenuService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SystemMenuService systemMenuService;
    @RequestMapping("/list")
    @RequiredPermission("角色列表")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model){
        model.addAttribute("pageResult",roleService.query(qo));
        return "/role/list";
    }
    @RequestMapping("/input")
    @RequiredPermission("角色编辑")
    public String input(Long id,Model model){
        model.addAttribute("role",roleService.get(id));
        model.addAttribute("permissions",permissionService.listAll());
        model.addAttribute("menus",systemMenuService.listAll());
        return "/role/input";
    }

    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新角色")
    @ResponseBody
    public JSONResult saveOrUpdate(Role role, Long[]permissionIds,Long[]menuIds){

        roleService.saveOrUpdate(role,permissionIds,menuIds);
        return new JSONResult();
    }
    @RequestMapping("/delete")
    @RequiredPermission("删除角色")
    @ResponseBody
    public JSONResult delete(Long id){
        if(id!=null) {
            roleService.delete(id);
        }
        return new JSONResult();
    }
}
