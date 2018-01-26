package com._520it.wms.web.controller;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.query.SystemMenuQueryObject;
import com._520it.wms.service.SystemMenuService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/systemMenu")
public class SystemMenuController {
    @Autowired
    private SystemMenuService systemMenuService;

    @RequestMapping("/list")
    @RequiredPermission("系统菜单列表")

    public String list(@ModelAttribute("qo") SystemMenuQueryObject qo, Model model) {
        if(qo.getParentId() !=null){
            SystemMenu parent = systemMenuService.get(qo.getParentId());
            List<Map<String,Object>> menus=systemMenuService.queryMenu(parent);
            model.addAttribute("menus",menus);
        }
        //------------------------------------------------------
        model.addAttribute("pageResult", systemMenuService.query(qo));
        return "/systemMenu/list";
    }


    @RequestMapping("/input")
    @RequiredPermission("系统菜单编辑")
    public String input(Long id, Long parentId, Model model) {
        if (parentId == null) {
            model.addAttribute("parentName", "根目录");
        } else {
            SystemMenu parent = systemMenuService.get(parentId);
            System.out.println(parent);
            model.addAttribute("parentName", parent.getName());
            model.addAttribute("parentId", parent.getId());
        }
        //-------------------------------------
        if (id != null) {
            model.addAttribute("systemMenu", systemMenuService.get(id));
            System.out.println(systemMenuService.get(id));
        }
        return "/systemMenu/input";
    }

    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("系统菜单保存或更改")
    @ResponseBody
    public JSONResult saveOrUpdate(SystemMenu sm, Long parentId) {
        if (parentId != null) {
            SystemMenu parent = new SystemMenu();
            parent.setId(parentId);
            sm.setParent(parent);
        }
        systemMenuService.saveOrUpdate(sm);
        return new JSONResult();
    }

    @RequestMapping("/delete")
    @RequiredPermission("系统菜单删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        if (id != null) {
            systemMenuService.delete(id);
        }
        return new JSONResult();
    }
    @RequestMapping("/loadMenusByParentSn")
    @ResponseBody
    //每个菜单
    public List<Map<String,Object>> loadMenusByParentSn(String parentSn){
        List<Map<String,Object>>list=systemMenuService.queryMenusByParentSn(parentSn);
            return list;

    }
}
