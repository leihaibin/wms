package com._520it.wms.web.controller;

import com._520it.wms.domain.Brand;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.BrandService;
import com._520it.wms.util.JSONResult;
import com._520it.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @RequestMapping("/list")
    @RequiredPermission("品牌列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        model.addAttribute("pageResult",brandService.query(qo));
        return "/brand/list";
    }
    @RequestMapping("/input")
    @RequiredPermission("品牌编辑")
    public String input(Long id,Model model){

        model.addAttribute("brand",brandService.get(id));
        return "/brand/input";
    }
    @RequestMapping("/saveOrUpdate")
    @RequiredPermission("保存/更新品牌")
    @ResponseBody
    public JSONResult saveOrUpdate(Brand brand){
        brandService.saveOrUpdate(brand);
        return new JSONResult();
    }
    @RequestMapping("/delete")
    @RequiredPermission("删除品牌")
    @ResponseBody
    public JSONResult delete(Long id){
        if(id!=null) {
            brandService.delete(id);
        }
        return new JSONResult();
    }
}
