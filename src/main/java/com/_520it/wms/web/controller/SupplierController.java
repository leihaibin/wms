package com._520it.wms.web.controller;

import com._520it.wms.domain.Supplier;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.SupplierService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @RequestMapping("/list")
    @RequiredPermission("供应商列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        model.addAttribute("pageResult",supplierService.query(qo));
        return "/supplier/list";
    }
    @RequestMapping("/input")
    @RequiredPermission("供应商编辑")
    public String input(Long id,Model model){

        model.addAttribute("supplier",supplierService.get(id));
        return "/supplier/input";
    }
    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新供应商")
    @ResponseBody
    public JSONResult saveOrUpdate(Supplier supplier){
        supplierService.saveOrUpdate(supplier);
        return new JSONResult();
    }
    @RequestMapping("/delete")
    @RequiredPermission("删除供应商")
    @ResponseBody
    public JSONResult delete(Long id){
        if(id!=null) {
            supplierService.delete(id);
        }
        return new JSONResult();
    }
}
