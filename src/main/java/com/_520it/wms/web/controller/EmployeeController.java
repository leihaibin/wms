package com._520it.wms.web.controller;

import com._520it.wms.domain.Employee;
import com._520it.wms.query.EasyPageResult;
import com._520it.wms.query.EmployeeQueryObject;
import com._520it.wms.service.DepartmentService;
import com._520it.wms.service.EmployeeService;
import com._520it.wms.service.RoleService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService   employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService       roleService;

    @RequestMapping("/list")
    @RequiredPermission("员工列表")
    public String list(@ModelAttribute("qo") EmployeeQueryObject qo, Model model) {
        model.addAttribute("depts", departmentService.listAll());
        model.addAttribute("pageResult", employeeService.query(qo));
        return "/employee/list";
    }

    @RequestMapping("/input")
    @RequiredPermission("员工编辑")
    public String input(Long id, Model model) {
        model.addAttribute("employee", employeeService.get(id));
        model.addAttribute("depts", departmentService.listAll());
        model.addAttribute("roles", roleService.listAll());
        return "/employee/input";
    }

    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新员工")
    @ResponseBody
    public JSONResult saveOrUpdate(Employee employee, Long[] roleIds) {

        employeeService.saveOrUpdate(employee, roleIds);
        return new JSONResult();
    }

    @RequestMapping("/delete")
    @RequiredPermission("删除员工")
    @ResponseBody
    public JSONResult delete(Long id) {
        if (id != null) {
            employeeService.delete(id);
        }
        return new JSONResult();
    }

    @RequestMapping("/batchDelete")
    @RequiredPermission("批量删除员工")
    @ResponseBody
    public JSONResult batchDelete(Long[] ids) {
        if (ids != null && ids.length > 0) {
            employeeService.batchDelete(ids);
        }
        return new JSONResult();
    }
    @RequestMapping("/easy")
    @ResponseBody
    public EasyPageResult easy(@ModelAttribute("qo")EmployeeQueryObject qo) {
        EasyPageResult rows=employeeService.easy(qo);
        return rows;
    }

}
