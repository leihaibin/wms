package com._520it.wms.web.controller;

import com._520it.wms.domain.Department;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.DepartmentService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @RequestMapping("/list")
    @RequiredPermission("部门列表")
    public String list(Model model, @ModelAttribute("qo")QueryObject qo){

        model.addAttribute("pageResult",departmentService.query(qo));
        return "/department/list";
    }
    @RequestMapping("/input")
    @RequiredPermission("部门编辑")
    public String input(Long id,Model model){
        model.addAttribute("department",departmentService.get(id));
        return "/department/input";
    }
    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新部门")
    @ResponseBody
    public JSONResult saveOrUpdate(Department department){
        departmentService.saveOrUpdate(department);
        return new JSONResult();
    }
    @RequestMapping("/delete")
    @RequiredPermission("删除部门")
    @ResponseBody
    public JSONResult delete(Long id){
        if(id!=null) {
            departmentService.delete(id);
        }
        return new JSONResult();
    }
}
